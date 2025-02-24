package com.obelix.homework.platform.config.exception;

public class SubjectNotFoundException extends RuntimeException {
    public static final String ERROR = "Subject not found";
    public SubjectNotFoundException(String message) {
        super(String.format("%s with id: %s", ERROR, message));
    }
}
