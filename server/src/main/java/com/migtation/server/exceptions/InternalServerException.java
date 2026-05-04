package com.migtation.server.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super();
    }
}
