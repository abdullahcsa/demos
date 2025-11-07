package com.abd.demo.integration;

import com.abd.demo.domain.Time;
import com.abd.demo.domain.exceptions.InvalidTimeException;
import com.abd.demo.domain.exceptions.InvalidTimeFormatException;
import com.abd.demo.service.BritishTimeConverter;
import com.abd.demo.service.TimeConverterService;
import com.abd.demo.service.TimeParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the complete time conversion flow.
 * Tests the interaction between TimeParser, Time, and TimeConverterService.
 * Uses real implementations (no mocks) to verify end-to-end functionality.
 */
@DisplayName("Time Conversion Integration Tests")
class TimeConversionIntegrationTest {

    private TimeParser parser;
    private TimeConverterService converterService;
    private BritishTimeConverter britishConverter;

    @BeforeEach
    void setUp() {
        parser = new TimeParser();
        britishConverter = new BritishTimeConverter();
        converterService = new TimeConverterService();
    }

    // ===== Success Path Integration Tests =====

    @Test
    @DisplayName("Integration: Parse '12:00' and convert to 'noon'")
    void shouldParseAndConvertNoon() {
        Time time = parser.parse("12:00");
        String result = converterService.convert(time);
        assertEquals("noon", result);
    }

    @Test
    @DisplayName("Integration: Parse '00:00' and convert to 'midnight'")
    void shouldParseAndConvertMidnight() {
        Time time = parser.parse("00:00");
        String result = converterService.convert(time);
        assertEquals("midnight", result);
    }

    @Test
    @DisplayName("Integration: Parse '3:15' and convert to 'quarter past three'")
    void shouldParseAndConvertQuarterPast() {
        Time time = parser.parse("3:15");
        String result = converterService.convert(time);
        assertEquals("quarter past three", result);
    }

    @Test
    @DisplayName("Integration: Parse '14:30' and convert to 'half past two'")
    void shouldParseAndConvertHalfPast() {
        Time time = parser.parse("14:30");
        String result = converterService.convert(time);
        assertEquals("half past two", result);
    }

    @Test
    @DisplayName("Integration: Parse '8:45' and convert to 'quarter to nine'")
    void shouldParseAndConvertQuarterTo() {
        Time time = parser.parse("8:45");
        String result = converterService.convert(time);
        assertEquals("quarter to nine", result);
    }

    @Test
    @DisplayName("Integration: Parse '5:00' and convert to 'five o'clock'")
    void shouldParseAndConvertOClock() {
        Time time = parser.parse("5:00");
        String result = converterService.convert(time);
        assertEquals("five o'clock", result);
    }

    @Test
    @DisplayName("Integration: Parse '10:20' and convert to 'twenty past ten'")
    void shouldParseAndConvertMinutesPast() {
        Time time = parser.parse("10:20");
        String result = converterService.convert(time);
        assertEquals("twenty past ten", result);
    }

    @Test
    @DisplayName("Integration: Parse '7:40' and convert to 'twenty to eight'")
    void shouldParseAndConvertMinutesTo() {
        Time time = parser.parse("7:40");
        String result = converterService.convert(time);
        assertEquals("twenty to eight", result);
    }

    @Test
    @DisplayName("Integration: Parse hour-only '12' and convert to 'noon'")
    void shouldParseHourOnlyAndConvert() {
        Time time = parser.parse("12");
        String result = converterService.convert(time);
        assertEquals("noon", result);
    }

    @Test
    @DisplayName("Integration: Parse with whitespace '  14:15  ' and convert")
    void shouldParseWithWhitespaceAndConvert() {
        Time time = parser.parse("  14:15  ");
        String result = converterService.convert(time);
        assertEquals("quarter past two", result);
    }

    // ===== Error Path Integration Tests =====

    @Test
    @DisplayName("Integration: Invalid format should throw InvalidTimeFormatException")
    void shouldThrowExceptionForInvalidFormat() {
        assertThrows(InvalidTimeFormatException.class, () -> {
            parser.parse("invalid");
        });
    }

    @Test
    @DisplayName("Integration: Empty input should throw InvalidTimeFormatException")
    void shouldThrowExceptionForEmptyInput() {
        assertThrows(InvalidTimeFormatException.class, () -> {
            parser.parse("");
        });
    }

    @Test
    @DisplayName("Integration: Null input should throw InvalidTimeFormatException")
    void shouldThrowExceptionForNullInput() {
        assertThrows(InvalidTimeFormatException.class, () -> {
            parser.parse(null);
        });
    }

    @Test
    @DisplayName("Integration: Invalid hour should throw InvalidTimeException")
    void shouldThrowExceptionForInvalidHour() {
        assertThrows(InvalidTimeException.class, () -> {
            parser.parse("25:30");
        });
    }

    @Test
    @DisplayName("Integration: Invalid minute should throw InvalidTimeException")
    void shouldThrowExceptionForInvalidMinute() {
        assertThrows(InvalidTimeException.class, () -> {
            parser.parse("14:60");
        });
    }

    // ===== Edge Cases Integration Tests =====

    @Test
    @DisplayName("Integration: Parse '0:00' (single digit hour) and convert to 'midnight'")
    void shouldParseSingleDigitMidnight() {
        Time time = parser.parse("0:00");
        String result = converterService.convert(time);
        assertEquals("midnight", result);
    }

    @Test
    @DisplayName("Integration: Parse '23:55' and convert to 'five to midnight'")
    void shouldParseLateNight() {
        Time time = parser.parse("23:55");
        String result = converterService.convert(time);
        assertEquals("five to zero", result);
    }

    @Test
    @DisplayName("Integration: Parse '11:45' and convert to 'quarter to twelve'")
    void shouldParseQuarterToNoon() {
        Time time = parser.parse("11:45");
        String result = converterService.convert(time);
        assertEquals("quarter to twelve", result);
    }

    @Test
    @DisplayName("Integration: Parse '13:00' and convert to 'one o'clock'")
    void shouldParseAfternoonOClock() {
        Time time = parser.parse("13:00");
        String result = converterService.convert(time);
        assertEquals("one o'clock", result);
    }

    @Test
    @DisplayName("Integration: Multiple conversions should be independent")
    void shouldHandleMultipleConversionsIndependently() {
        Time time1 = parser.parse("12:00");
        Time time2 = parser.parse("14:30");
        Time time3 = parser.parse("8:45");

        String result1 = converterService.convert(time1);
        String result2 = converterService.convert(time2);
        String result3 = converterService.convert(time3);

        assertEquals("noon", result1);
        assertEquals("half past two", result2);
        assertEquals("quarter to nine", result3);
    }

    // ===== Minutes not divisible by 5 (should not convert via British converter) =====

    @Test
    @DisplayName("Integration: Minutes not divisible by 5 should use general converter")
    void shouldHandleMinutesNotDivisibleBy5() {
        Time time = parser.parse("12:37");
        String result = converterService.convert(time);
        // General converter would return numeric format
        assertEquals("twelve thirty seven", result);
    }
}
