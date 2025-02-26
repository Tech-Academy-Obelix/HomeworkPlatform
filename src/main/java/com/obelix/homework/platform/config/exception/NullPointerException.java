package com.obelix.homework.platform.config.exception;

public class NullPointerException extends RuntimeException{
    public static final String ERROR = "Not found";
    public NullPointerException(String message) {
        super(String.format(message));
    }
}
