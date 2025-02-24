package com.obelix.homework.platform.config.exception;

public class AssignmentNotFoundException extends RuntimeException {
    public static final String ERROR = "Homework assigment not found";
    public AssignmentNotFoundException(String message) {
        super(String.format("%s with id: %s", ERROR, message));
    }
}
