package com.obelix.homework.platform.config.exception;

public class NoSuchRoleException extends RuntimeException {
    public NoSuchRoleException(String role) {
        super(String.format("Role doesn't exist with name: %s", role));
    }
}
