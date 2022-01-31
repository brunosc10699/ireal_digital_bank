package com.bruno.bdb.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
public class InactiveAccountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InactiveAccountException(String message) {
        super(message);
    }
}
