package com.oga.cqrsref.exceptions;

public class MultipleCommandHandlersException extends RuntimeException {
    public MultipleCommandHandlersException(String message) {
        super(message);
    }

    public MultipleCommandHandlersException() {
    }
}
