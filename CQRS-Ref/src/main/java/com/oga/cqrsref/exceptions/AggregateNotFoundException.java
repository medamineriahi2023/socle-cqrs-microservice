package com.oga.cqrsref.exceptions;

public class AggregateNotFoundException extends RuntimeException {
    public AggregateNotFoundException(String message) {
        super(message);
    }
}