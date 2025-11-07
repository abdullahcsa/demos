package com.abd.demo.domain.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TimeConversionException Tests")
class TimeConversionExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void shouldCreateExceptionWithMessage() {
        String message = "No rule matched for time: 12:37";
        TimeConversionException exception = new TimeConversionException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Should create exception with message and cause")
    void shouldCreateExceptionWithMessageAndCause() {
        String message = "Time conversion failed";
        Throwable cause = new IllegalStateException("No converter available");
        TimeConversionException exception = new TimeConversionException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Should be a RuntimeException")
    void shouldBeRuntimeException() {
        TimeConversionException exception = new TimeConversionException("Test message");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should allow null message")
    void shouldAllowNullMessage() {
        TimeConversionException exception = new TimeConversionException(null);
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should allow empty message")
    void shouldAllowEmptyMessage() {
        TimeConversionException exception = new TimeConversionException("");
        assertEquals("", exception.getMessage());
    }

    @Test
    @DisplayName("Should preserve cause chain")
    void shouldPreserveCauseChain() {
        Throwable rootCause = new NullPointerException("Root cause");
        Throwable intermediateCause = new IllegalStateException("Intermediate", rootCause);
        TimeConversionException exception = new TimeConversionException("Final message", intermediateCause);

        assertEquals("Final message", exception.getMessage());
        assertEquals(intermediateCause, exception.getCause());
        assertEquals(rootCause, exception.getCause().getCause());
    }

    @Test
    @DisplayName("Should be thrown when no rule matches")
    void shouldBeThrownWhenNoRuleMatches() {
        TimeConversionException exception = assertThrows(
            TimeConversionException.class,
            () -> { throw new TimeConversionException("No rule matched for time: 12:37"); }
        );

        assertTrue(exception.getMessage().contains("No rule matched"));
    }
}
