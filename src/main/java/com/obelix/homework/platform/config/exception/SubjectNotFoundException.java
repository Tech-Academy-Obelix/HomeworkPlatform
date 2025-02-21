package com.obelix.homework.platform.config.exception;

public class SubjectNotFoundException extends RuntimeException {
    public SubjectNotFoundException(String message) {
        super(String.format("Subject not found with id: %s", message));
    }
}
