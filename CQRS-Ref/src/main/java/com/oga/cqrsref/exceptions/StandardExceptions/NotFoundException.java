package com.oga.cqrsref.exceptions.StandardExceptions;



public class NotFoundException extends Exception {
    private ApiErrorCodes errorCode;

    public NotFoundException(ApiErrorCodes errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiErrorCodes getErrorCode() {
        return errorCode;
    }
}