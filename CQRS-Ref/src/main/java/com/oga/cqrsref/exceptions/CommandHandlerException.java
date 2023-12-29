package com.oga.cqrsref.exceptions;

public class CommandHandlerException extends RuntimeException {
    public CommandHandlerException(String message) {
        super(message);
    }

    public CommandHandlerException() {
    }
}
