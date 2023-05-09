package com.textract.api.view.controllers;


import com.textract.api.domain.services.CleansingService;
import com.textract.api.view.responses.CleanedIDResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
public class ExtractionController {

    private final CleansingService cleansingService;

    public ExtractionController(CleansingService cleansingService) {
        this.cleansingService = cleansingService;
    }

    @PostMapping(path = "/upload")
    public ResponseEntity<CleanedIDResponse> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cleansingService.extractText(file));
    }
}
