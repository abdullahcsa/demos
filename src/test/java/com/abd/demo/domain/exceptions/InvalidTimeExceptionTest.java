package com.abd.demo.domain.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InvalidTimeException Tests")
class InvalidTimeExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void shouldCreateExceptionWithMessage() {
        String message = "Hours must be between 0 and 23, got: 25";
        InvalidTimeException exception = new InvalidTimeException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Should create exception with message and cause")
    void shouldCreateExceptionWithMessageAndCause() {
        String message = "Invalid time value";
        Throwable cause = new NumberFormatException("Invalid number");
        InvalidTimeException exception = new InvalidTimeException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Should be a RuntimeException")
    void shouldBeRuntimeException() {
        InvalidTimeException exception = new InvalidTimeException("Test message");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should allow null message")
    void shouldAllowNullMessage() {
        InvalidTimeException exception = new InvalidTimeException(null);
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should allow empty message")
    void shouldAllowEmptyMessage() {
        InvalidTimeException exception = new InvalidTimeException("");
        assertEquals("", exception.getMessage());
    }

    @Test
    @DisplayName("Should preserve cause chain")
    void shouldPreserveCauseChain() {
        Throwable rootCause = new IllegalStateException("Root cause");
        Throwable intermediateCause = new RuntimeException("Intermediate", rootCause);
        InvalidTimeException exception = new InvalidTimeException("Final message", intermediateCause);

        assertEquals("Final message", exception.getMessage());
        assertEquals(intermediateCause, exception.getCause());
        assertEquals(rootCause, exception.getCause().getCause());
    }
}
