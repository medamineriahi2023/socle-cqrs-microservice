package com.oga.cqrsref.exceptions;

public class NoQueryHandlerException extends RuntimeException {

    public NoQueryHandlerException(String message) {
        super(message);
    }

    public NoQueryHandlerException() {
    }
}
