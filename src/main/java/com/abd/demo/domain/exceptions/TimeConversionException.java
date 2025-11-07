package com.abd.demo.domain.exceptions;

/**
 * Business exception thrown when time conversion to words fails.
 * Represents a failure in the conversion process, typically when no conversion rule matches.
 */
public class TimeConversionException extends RuntimeException {

    public TimeConversionException(String message) {
        super(message);
    }

    public TimeConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
