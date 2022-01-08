package com.leverx.proxypets.exception.custom;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(code = NOT_FOUND, reason = "Pet not found")
public class PetNotFoundException extends EntityNotFoundException {

    public PetNotFoundException(String message) {
        super(message);
    }
}
