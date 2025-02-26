package com.obelix.homework.platform.config.exception;

public class InviteCodeNotFoundException extends RuntimeException {
    public static final String ERROR = "Invite code not found";
    public InviteCodeNotFoundException(String message) {
        super(String.format("%s: %s", ERROR, message));
    }
}
