package com.textract.api.domain.services;

import com.textract.api.domain.exceptions.ExtractionException;
import com.textract.api.domain.mappers.IdentityMapper;
import com.textract.api.domain.repositories.IdentityRepository;
import com.textract.api.view.responses.CleanedIDResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class StorageService {

    private final IdentityRepository identityRepository;

    private final IdentityMapper mapper;

    public StorageService(IdentityRepository identityRepository, IdentityMapper mapper) {
        this.identityRepository = identityRepository;
        this.mapper = mapper;
    }

    //it will save only if the identity number was extracted.
    public CleanedIDResponse saveIdentity(CleanedIDResponse dto) {
        if (Objects.isNull(dto.getIdentityNumber())) {
            log.info("Cannot save identity w/o identity number. Retrieving only.");
            return dto;
        }
        try {
            identityRepository.save(mapper.dtoToEntity(dto));
        } catch (Exception e) {
            log.error("Could not execute statement");
            throw new ExtractionException(dto.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Entity saved.");

        return dto;
    }
}
