package com.obelix.homework.platform.exception;

public class NoSuchRoleException extends RuntimeException {
    public NoSuchRoleException(String role) {
        super(String.format("Role with name %s does not exist", role));
    }
}
