package com.maybe.maybe.exception;

public class UnmodifiedEntityException extends RuntimeException {
    public UnmodifiedEntityException(String errorMessage) {
        super(errorMessage);
    }
}