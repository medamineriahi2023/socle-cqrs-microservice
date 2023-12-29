package com.oga.cqrsref.exceptions;


public class MultipleQueryHandlersException extends RuntimeException {

    public MultipleQueryHandlersException(String message) {
        super(message);
    }

    public MultipleQueryHandlersException() {
    }
}
