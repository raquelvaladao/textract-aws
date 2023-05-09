package com.textract.api.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExtractionException extends RuntimeException{

    private String message;
    private HttpStatus statusCode;

    public ExtractionException(String message,HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ExtractionException() {
    }

    public ExtractionException(String message) {
        super(message);
    }
}
