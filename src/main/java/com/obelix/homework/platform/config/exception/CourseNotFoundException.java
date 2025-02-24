package com.obelix.homework.platform.config.exception;

public class CourseNotFoundException extends RuntimeException {
    public static final String ERROR = "Course not found";
    public CourseNotFoundException(String message) {
        super(String.format("%s id: %s", ERROR, message));
    }
}
