package com.abd.demo.domain.exceptions;

/**
 * Business exception thrown when a Time value object has invalid hours or minutes.
 * Represents a domain validation failure for time values.
 */
public class InvalidTimeException extends RuntimeException {

    public InvalidTimeException(String message) {
        super(message);
    }

    public InvalidTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
