package com.oga.ged.command.rest.utils.exceptions;

public class FileProcessingException extends RuntimeException {
    private String message;
    private Throwable cause;

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}