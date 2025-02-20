package com.obelix.homework.platform.config.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(String.format("Course not found with id: %s", message));
    }
}
