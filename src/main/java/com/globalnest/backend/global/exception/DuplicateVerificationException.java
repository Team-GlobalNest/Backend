package com.globalnest.backend.global.exception;

public class DuplicateVerificationException extends RuntimeException {
    public DuplicateVerificationException(String message) {
        super(message);
    }
}
