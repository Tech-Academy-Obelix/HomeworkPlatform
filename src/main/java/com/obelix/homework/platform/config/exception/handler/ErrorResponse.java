package com.obelix.homework.platform.config.exception.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final LocalDateTime timeStamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String message;
}
