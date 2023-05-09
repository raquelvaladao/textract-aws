package com.textract.api.view.controllers;


import com.textract.api.domain.exceptions.ExtractionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExtractionHandlerController {

    @ExceptionHandler(value
            = { ExtractionException.class })
    protected ResponseEntity<Object> handleExtractionException(ExtractionException ex, WebRequest request) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }

    @ExceptionHandler(value
            = { RuntimeException.class })
    protected ResponseEntity<Object> handleGenericException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
