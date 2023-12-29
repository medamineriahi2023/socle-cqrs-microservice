package com.oga.ged.query.rest.utils.exceptions;

/**
 * Exception thrown when a folder is not found.
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
