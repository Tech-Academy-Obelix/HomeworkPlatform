package com.obelix.homework.platform.config.exception;

public class ResubmitionException extends RuntimeException {
    public static final String ERROR = "Resubmition not allowed";
    public ResubmitionException(String message) {
        super(String.format("There already exists a submission for assignment with id: %s", message));
    }
}
