package com.obelix.homework.platform.exception;

import java.io.Serial;

public class UsernameExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameExistsException(String username) {
        super(String.format("Username %s is already in use.", username));
    }
}
