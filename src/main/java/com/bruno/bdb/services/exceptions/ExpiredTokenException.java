package com.bruno.bdb.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExpiredTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExpiredTokenException(String message) {
        super(message);
    }
}
