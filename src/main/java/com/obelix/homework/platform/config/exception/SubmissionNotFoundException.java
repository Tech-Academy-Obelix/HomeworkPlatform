package com.obelix.homework.platform.config.exception;

public class SubmissionNotFoundException extends RuntimeException {
    public static final String ERROR = "Submission not found";
    public SubmissionNotFoundException(String message) {
        super(String.format("%s with id: %s", ERROR, message));
    }
}
