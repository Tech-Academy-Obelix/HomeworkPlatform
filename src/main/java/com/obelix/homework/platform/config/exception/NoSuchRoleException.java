package com.obelix.homework.platform.config.exception;

public class NoSuchRoleException extends RuntimeException {
    public static final String ERROR = "Role does not exist";
    public NoSuchRoleException(String role) {
        super(String.format("%s with name: %s", ERROR, role));
    }
}
