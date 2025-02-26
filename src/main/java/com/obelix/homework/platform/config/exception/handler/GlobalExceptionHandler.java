package com.obelix.homework.platform.config.exception.handler;

import com.obelix.homework.platform.config.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.FileNotFoundException;
import java.lang.NullPointerException;
import java.lang.SecurityException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AssignmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAssignmentNotFoundException(AssignmentNotFoundException ex) {
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, AssignmentNotFoundException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCourseNotFoundException(CourseNotFoundException ex) {
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, CourseNotFoundException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(NoSuchRoleException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchRoleException(NoSuchRoleException ex) {
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, NoSuchRoleException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubjectNotFoundException(SubjectNotFoundException ex) {
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, SubjectNotFoundException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameExistsException(UsernameExistsException ex) {
        int status = HttpStatus.CONFLICT.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, UsernameExistsException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, UserNotFoundException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(SubjectHasAssignedTeacherException.class)
    public ResponseEntity<ErrorResponse> handleSubjectHasAssignedTeacherException(SubjectHasAssignedTeacherException ex) {
        int status = HttpStatus.CONFLICT.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, SubjectHasAssignedTeacherException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        LOGGER.error("NullPointerException: ", e);
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, "Internal Server Error", e.getMessage()));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("File Not Found: " + ex.getMessage());
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleSecurityException(SecurityException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Security Error: " + ex.getMessage());
    }

    @ExceptionHandler(InviteCodeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInviteCodeNotFoundException(InviteCodeNotFoundException ex) {
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, InviteCodeNotFoundException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException() {
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, "Page Not Found", "This page doesn't seem to exist"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, "Invalid Request",
                        ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        LOGGER.error("Exception: ", ex);
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, "Internal Server Error", "Oops! Something went wrong."));
    }
}
