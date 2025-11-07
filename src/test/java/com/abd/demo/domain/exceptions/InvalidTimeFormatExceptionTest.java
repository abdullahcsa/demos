package com.abd.demo.domain.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InvalidTimeFormatException Tests")
class InvalidTimeFormatExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void shouldCreateExceptionWithMessage() {
        String message = "Invalid time format. Expected format: HH:MM or H:M";
        InvalidTimeFormatException exception = new InvalidTimeFormatException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Should create exception with message and cause")
    void shouldCreateExceptionWithMessageAndCause() {
        String message = "Invalid time format. Hours and minutes must be numbers.";
        Throwable cause = new NumberFormatException("For input string: \"abc\"");
        InvalidTimeFormatException exception = new InvalidTimeFormatException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Should be a RuntimeException")
    void shouldBeRuntimeException() {
        InvalidTimeFormatException exception = new InvalidTimeFormatException("Test message");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should allow null message")
    void shouldAllowNullMessage() {
        InvalidTimeFormatException exception = new InvalidTimeFormatException(null);
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should allow empty message")
    void shouldAllowEmptyMessage() {
        InvalidTimeFormatException exception = new InvalidTimeFormatException("");
        assertEquals("", exception.getMessage());
    }

    @Test
    @DisplayName("Should wrap NumberFormatException as cause")
    void shouldWrapNumberFormatException() {
        NumberFormatException nfe = new NumberFormatException("Cannot parse");
        InvalidTimeFormatException exception = new InvalidTimeFormatException("Parsing failed", nfe);

        assertEquals("Parsing failed", exception.getMessage());
        assertSame(nfe, exception.getCause());
        assertTrue(exception.getCause() instanceof NumberFormatException);
    }

    @Test
    @DisplayName("Should preserve cause chain")
    void shouldPreserveCauseChain() {
        Throwable rootCause = new IllegalArgumentException("Root cause");
        Throwable intermediateCause = new NumberFormatException("Intermediate");
        intermediateCause.initCause(rootCause);
        InvalidTimeFormatException exception = new InvalidTimeFormatException("Final message", intermediateCause);

        assertEquals("Final message", exception.getMessage());
        assertEquals(intermediateCause, exception.getCause());
        assertEquals(rootCause, exception.getCause().getCause());
    }
}
