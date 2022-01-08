package com.leverx.proxypets.exception.custom;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(code = BAD_REQUEST, reason = "Pet not found")
public class SimilarPersonException extends RuntimeException{
    public SimilarPersonException(String message) {
        super(message);
    }
}
