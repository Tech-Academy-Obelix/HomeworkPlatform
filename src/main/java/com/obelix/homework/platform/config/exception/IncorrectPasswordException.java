package com.obelix.homework.platform.config.exception;

public class IncorrectPasswordException extends RuntimeException {
    public static final String ERROR = "Incorrect password";
    public IncorrectPasswordException(String message) {
        super(String.format("%s for user: %s", ERROR, message));
    }
}
