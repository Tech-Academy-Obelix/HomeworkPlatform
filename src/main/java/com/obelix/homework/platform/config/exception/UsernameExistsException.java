package com.obelix.homework.platform.config.exception;

public class UsernameExistsException extends RuntimeException {
    public static final String ERROR = "Username already in use";
    public UsernameExistsException(String message) {
        super(String.format("%s: %s", ERROR, message));
    }
}
