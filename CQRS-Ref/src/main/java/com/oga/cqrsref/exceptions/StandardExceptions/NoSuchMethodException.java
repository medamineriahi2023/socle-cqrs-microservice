package com.oga.cqrsref.exceptions.StandardExceptions;

public class NoSuchMethodException extends Exception {
    private ApiErrorCodes errorCode;

    public NoSuchMethodException(ApiErrorCodes errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiErrorCodes getErrorCode() {
        return errorCode;
    }
}
