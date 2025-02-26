package com.obelix.homework.platform.config.exception;

public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(String message){
        super(String.format(message));
    }
}
