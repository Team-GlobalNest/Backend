package com.globalnest.backend.global.exception;

public class DuplicateBookmarkException extends RuntimeException {
    public DuplicateBookmarkException(String message) {
        super(message);
    }
}
