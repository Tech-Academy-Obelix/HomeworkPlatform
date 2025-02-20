package com.obelix.homework.platform.config.exception;

public class AssignmentNotFoundException extends RuntimeException {
    public AssignmentNotFoundException(String message) {
        super(String.format("Homework assignment not found with id: %s", message));
    }
}
