package com.abd.demo.domain.exceptions;

/**
 * Business exception thrown when parsing a time string fails due to invalid format.
 * Represents a domain validation failure for time input format.
 */
public class InvalidTimeFormatException extends RuntimeException {

    public InvalidTimeFormatException(String message) {
        super(message);
    }

    public InvalidTimeFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
