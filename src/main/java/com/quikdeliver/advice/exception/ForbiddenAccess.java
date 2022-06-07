package com.quikdeliver.advice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenAccess extends RuntimeException{
    public ForbiddenAccess(String message) {
        super(message);
    }

    public ForbiddenAccess(String message, Throwable cause) {
        super(message, cause);
    }
}
