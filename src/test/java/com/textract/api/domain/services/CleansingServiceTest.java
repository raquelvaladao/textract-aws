package com.textract.api.domain.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CleansingServiceTest {

    @Mock
    private TextractService textractService;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private CleansingService cleansingService;

    @Test
    void extractText() {
        assertNotNull(cleansingService);
    }
}