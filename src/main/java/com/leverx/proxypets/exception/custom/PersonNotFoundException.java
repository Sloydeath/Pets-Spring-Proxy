package com.leverx.proxypets.exception.custom;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(code = NOT_FOUND, reason = "Person not found")
public class PersonNotFoundException extends EntityNotFoundException {

    public PersonNotFoundException(String message) {
        super(message);
    }
}
