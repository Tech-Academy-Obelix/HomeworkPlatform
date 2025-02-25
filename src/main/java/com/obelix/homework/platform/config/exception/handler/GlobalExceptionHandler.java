package com.obelix.homework.platform.config.exception.handler;

import com.obelix.homework.platform.config.exception.*;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AssignmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAssignmentNotFoundException(AssignmentNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, AssignmentNotFoundException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCourseNotFoundException(CourseNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, CourseNotFoundException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(NoSuchRoleException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchRoleException(NoSuchRoleException ex) {
        var status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, NoSuchRoleException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubjectNotFoundException(SubjectNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, SubjectNotFoundException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameExistsException(UsernameExistsException ex) {
        var status = HttpStatus.CONFLICT.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, UsernameExistsException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, UserNotFoundException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(SubjectHasAssignedTeacherException.class)
    public ResponseEntity<ErrorResponse> handleSubjectHasAssignedTeacherException(SubjectHasAssignedTeacherException ex) {
        var status = HttpStatus.CONFLICT.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, SubjectHasAssignedTeacherException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(InviteCodeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInviteCodeNotFoundException(InviteCodeNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, InviteCodeNotFoundException.ERROR, ex.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException() {
        var status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, "Page Not Found", "This page doesn't seem to exist"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, "Invalid Request", ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        LoggerFactory.getLogger(GlobalExceptionHandler.class).error(ex.getMessage(), ex);
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, "Internal Server Error", "Oopsie, something went wrong :("));
    }
}
