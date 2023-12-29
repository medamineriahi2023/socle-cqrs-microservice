package com.oga.cqrsref.exceptions.StandardExceptions;

public class NullPointerException extends Exception {
    private ApiErrorCodes errorCode;

    public NullPointerException(ApiErrorCodes errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiErrorCodes getErrorCode() {
        return errorCode;
    }
}