package com.textract.api.domain.services;


import com.textract.api.shared.config.AwsLoaderConfig;
import com.textract.api.domain.exceptions.ExtractionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.AnalyzeDocumentRequest;
import software.amazon.awssdk.services.textract.model.AnalyzeDocumentResponse;
import software.amazon.awssdk.services.textract.model.Document;
import software.amazon.awssdk.services.textract.model.FeatureType;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TextractService {

    private final TextractClient client;

    public TextractService() {
        AwsLoaderConfig loader = new AwsLoaderConfig();
        this.client = TextractClient.builder()
                .credentialsProvider(loader.awsEnvironmentLoader())
                .region(Region.US_EAST_1)
                .build();
    }

    public AnalyzeDocumentResponse getRawExtractionInType(MultipartFile file, FeatureType extractionType) {
        SdkBytes sourceBytes;
        try {
            sourceBytes = SdkBytes.fromInputStream(file.getInputStream());
        } catch (IOException e) {
            throw new ExtractionException("Cannot accept null file.");
        }

        Document myDoc = Document.builder()
                .bytes(sourceBytes)
                .build();

        AnalyzeDocumentRequest analyzeDocumentRequest = AnalyzeDocumentRequest.builder()
                .document(myDoc)
                .featureTypes(List.of(extractionType))
                .build();
        log.info("Started calling client :: {}", Date.from(Instant.now()));

        AnalyzeDocumentResponse textResponse = client.analyzeDocument(analyzeDocumentRequest);

        log.info("Finished calling client :: {}", Date.from(Instant.now()));

        return textResponse;
    }

}
