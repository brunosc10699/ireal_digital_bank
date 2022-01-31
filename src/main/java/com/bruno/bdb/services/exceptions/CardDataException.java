package com.bruno.bdb.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CardDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CardDataException(String message) {
        super(message);
    }
}
