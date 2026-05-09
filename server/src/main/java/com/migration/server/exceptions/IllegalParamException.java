package com.migration.server.exceptions;

public class IllegalParamException extends RuntimeException {
    public IllegalParamException(String message) {
        super(message);
    }
}

