package com.obelix.homework.platform.config.exception;

public class SubjectHasAssignedTeacherException extends RuntimeException {
    public static final String ERROR = "Teacher already assigned to subject";
    public SubjectHasAssignedTeacherException(String message) {
        super(String.format("%s with id: %s.", ERROR, message));
    }
}
