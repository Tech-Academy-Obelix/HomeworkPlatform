package com.obelix.homework.platform.config.exception;

public class UserNotFoundException extends RuntimeException {
    public static final String ERROR = "User not found";
    public UserNotFoundException(String message) {
        super(String.format("%s with id: %s", ERROR, message));
    }
}
