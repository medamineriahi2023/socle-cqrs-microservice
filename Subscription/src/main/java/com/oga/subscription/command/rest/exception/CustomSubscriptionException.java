package com.oga.subscription.command.rest.exception;
public class CustomSubscriptionException extends RuntimeException {
    public CustomSubscriptionException(String message, Throwable cause) {
        super(message, cause);
    }
}

