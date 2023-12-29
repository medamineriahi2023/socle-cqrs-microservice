package com.oga.ged.query.rest.utils.exceptions;

/**
 * Exception thrown when an invalid ID is encountered.
 */
public class InvalidIdException extends RuntimeException {
    public InvalidIdException(String message, NumberFormatException e) {
        super(message);
    }
}





