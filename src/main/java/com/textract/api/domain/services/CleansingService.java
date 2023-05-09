package com.textract.api.domain.services;

import com.textract.api.domain.exceptions.ExtractionException;
import com.textract.api.view.responses.CleanedIDResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.textract.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CleansingService {

    public static final String NAME = "NOME";
    public static final String IDENTITY_NUMBER = "REGISTRO";
    public static final String BIRTHDATE = "NASCIMENTO";
    public static final double MINIMUM_CONFIDENCE_LEVEL = 68.0;

    private TextractService textractService;

    private StorageService storageService;

    public CleansingService(TextractService textractService, StorageService storageService) {
        this.textractService = textractService;
        this.storageService = storageService;
    }

    public CleanedIDResponse extractText(MultipartFile file) {
        CleanedIDResponse idRes = new CleanedIDResponse();

        try {
            AnalyzeDocumentResponse textResponse = textractService.getRawExtractionInType(file, FeatureType.FORMS);
            extractCleanedResponse(idRes, textResponse);
            storageService.saveIdentity(idRes);
        } catch (TextractException e) {
            throw new ExtractionException(e.getMessage());
        }
        return idRes;
    }

    private String extractValue(Block keyValPair, Map<String, Block> map) {
        List<String> blockIds = keyValPair.relationships().get(0).ids();

        List<Block> keyChilds = new ArrayList<>();
        for (String blockId : blockIds) {
            keyChilds.add(map.get(blockId));
        }

        List<String> ids = new ArrayList<>();

        for (Block childs : keyChilds) {
            childs.relationships().forEach(relationship -> ids.addAll(relationship.ids()));
        }

        List<String> words = new ArrayList<>();
        for (String id : ids) {
            Block block = map.get(id);

            if(isWordTrustable(block))
                words.add(block.text());
        }

        return String.join(" ", words);
    }

    private static boolean isWordTrustable(Block block) {
        return !(block.confidence() < MINIMUM_CONFIDENCE_LEVEL || Strings.isBlank(block.text()));
    }

    private String extractKey(Block keyValPair, Map<String, Block> map) {
        List<String> keyNameIds = keyValPair.relationships().get(1).ids();
        String full = "";

        for (String keyNameId : keyNameIds) {
            full = full.concat(map.get(keyNameId).text());
        }
        return String.join(" ", full);
    }

    private boolean hasKeyType(Block keyValPair) {
        return !keyValPair.entityTypes().isEmpty() && keyValPair.entityTypes().get(0).equals(EntityType.KEY);
    }

    private boolean hasIdentityFields(String fullKey) {
        return fullKey.contains(NAME) || fullKey.contains(IDENTITY_NUMBER) || fullKey.contains(BIRTHDATE);
    }

    private void extractCleanedResponse(CleanedIDResponse item, AnalyzeDocumentResponse textResponse) {
        Map<String, Block> map = new HashMap<>();
        List<Block> forms = textResponse.blocks().stream()
                .filter(form -> form.blockType().equals(BlockType.KEY_VALUE_SET) || form.blockType().equals(BlockType.WORD))
                .collect(Collectors.toList());

        forms.forEach(block -> {
            map.put(block.id(), block);
        });

        for (Block keyValPair : forms) {
            if (hasKeyType(keyValPair)) {
                String fullKey = extractKey(keyValPair, map);
                if (hasIdentityFields(fullKey)) {
                    String fullValue = extractValue(keyValPair, map);
                    setCleanedFields(item, fullValue, fullKey);
                }
            }
        }
    }

    private void setCleanedFields(CleanedIDResponse item, String value, String key) {
        if (key.contains(NAME))
            item.setFullName(value);

        if (key.contains(IDENTITY_NUMBER))
            item.setIdentityNumber(value);

        if (key.contains(BIRTHDATE))
            item.setBirthDate(value);
    }

}
