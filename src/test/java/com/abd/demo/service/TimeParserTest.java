package com.abd.demo.service;

import com.abd.demo.domain.Time;
import com.abd.demo.domain.exceptions.InvalidTimeException;
import com.abd.demo.domain.exceptions.InvalidTimeFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TimeParser Service Tests")
public class TimeParserTest {

    private final TimeParser parser = new TimeParser();

    @Test
    @DisplayName("Should parse valid time in HH:MM format")
    public void testParseValidTimeHHMM() {
        Time time = parser.parse("14:30");
        assertEquals(14, time.getHours());
        assertEquals(30, time.getMinutes());
    }

    @Test
    @DisplayName("Should parse time with single digit hour")
    public void testParseValidTimeSingleDigitHour() {
        Time time = parser.parse("9:30");
        assertEquals(9, time.getHours());
        assertEquals(30, time.getMinutes());
    }

    @Test
    @DisplayName("Should parse time with single digit minute")
    public void testParseValidTimeSingleDigitMinute() {
        Time time = parser.parse("14:05");
        assertEquals(14, time.getHours());
        assertEquals(5, time.getMinutes());
    }

    @Test
    @DisplayName("Should parse time with single digit hour and minute")
    public void testParseValidTimeSingleDigitBoth() {
        Time time = parser.parse("9:5");
        assertEquals(9, time.getHours());
        assertEquals(5, time.getMinutes());
    }

    @Test
    @DisplayName("Should parse midnight (0:00)")
    public void testParseMidnight() {
        Time time = parser.parse("0:00");
        assertEquals(0, time.getHours());
        assertEquals(0, time.getMinutes());
    }

    @Test
    @DisplayName("Should parse end of day (23:59)")
    public void testParseEndOfDay() {
        Time time = parser.parse("23:59");
        assertEquals(23, time.getHours());
        assertEquals(59, time.getMinutes());
    }

    @Test
    @DisplayName("Should parse time with leading zeros")
    public void testParseWithLeadingZeros() {
        Time time = parser.parse("09:05");
        assertEquals(9, time.getHours());
        assertEquals(5, time.getMinutes());
    }

    @Test
    @DisplayName("Should parse time with surrounding whitespace")
    public void testParseWithWhitespace() {
        Time time = parser.parse("  14:30  ");
        assertEquals(14, time.getHours());
        assertEquals(30, time.getMinutes());
    }

    // Tests for hour-only format (H or HH)
    @Test
    @DisplayName("Should parse single digit hour (5 -> 5:00)")
    public void testParseHourOnlySingleDigit() {
        Time time = parser.parse("5");
        assertEquals(5, time.getHours());
        assertEquals(0, time.getMinutes());
    }

    @Test
    @DisplayName("Should parse double digit hour (12 -> 12:00)")
    public void testParseHourOnlyDoubleDigit() {
        Time time = parser.parse("12");
        assertEquals(12, time.getHours());
        assertEquals(0, time.getMinutes());
    }

    @Test
    @DisplayName("Should parse midnight hour only (0 -> 0:00)")
    public void testParseHourOnlyMidnight() {
        Time time = parser.parse("0");
        assertEquals(0, time.getHours());
        assertEquals(0, time.getMinutes());
    }

    @Test
    @DisplayName("Should parse end of day hour only (23 -> 23:00)")
    public void testParseHourOnlyEndOfDay() {
        Time time = parser.parse("23");
        assertEquals(23, time.getHours());
        assertEquals(0, time.getMinutes());
    }

    @Test
    @DisplayName("Should parse hour only with leading zero (09 -> 9:00)")
    public void testParseHourOnlyWithLeadingZero() {
        Time time = parser.parse("09");
        assertEquals(9, time.getHours());
        assertEquals(0, time.getMinutes());
    }

    @Test
    @DisplayName("Should parse hour only with whitespace (  5  -> 5:00)")
    public void testParseHourOnlyWithWhitespace() {
        Time time = parser.parse("  5  ");
        assertEquals(5, time.getHours());
        assertEquals(0, time.getMinutes());
    }

    @Test
    @DisplayName("Should throw exception for multiple colons")
    public void testParseInvalidFormatMultipleColons() {
        assertThrows(InvalidTimeFormatException.class, () -> {
            parser.parse("14:30:00");
        });
    }

    @Test
    @DisplayName("Should throw exception for non-numeric input")
    public void testParseInvalidFormatLetters() {
        assertThrows(InvalidTimeFormatException.class, () -> {
            parser.parse("ab:cd");
        });
    }

    @Test
    @DisplayName("Should throw exception for empty input")
    public void testParseInvalidFormatEmpty() {
        assertThrows(InvalidTimeFormatException.class, () -> {
            parser.parse("");
        });
    }

    @Test
    @DisplayName("Should throw exception for null input")
    public void testParseInvalidFormatNull() {
        assertThrows(InvalidTimeFormatException.class, () -> {
            parser.parse(null);
        });
    }

    @Test
    @DisplayName("Should throw exception for hour >= 24")
    public void testParseInvalidHourTooLarge() {
        assertThrows(InvalidTimeException.class, () -> {
            parser.parse("24:30");
        });
    }

    @Test
    @DisplayName("Should throw exception for minute >= 60")
    public void testParseInvalidMinuteTooLarge() {
        assertThrows(InvalidTimeException.class, () -> {
            parser.parse("14:60");
        });
    }

    @Test
    @DisplayName("Should throw exception for negative hour")
    public void testParseInvalidNegativeHour() {
        assertThrows(InvalidTimeException.class, () -> {
            parser.parse("-1:30");
        });
    }

    @Test
    @DisplayName("Should throw exception for negative minute")
    public void testParseInvalidNegativeMinute() {
        assertThrows(InvalidTimeException.class, () -> {
            parser.parse("14:-5");
        });
    }

    @Test
    @DisplayName("Should throw exception for only colon")
    public void testParseInvalidFormatOnlyColon() {
        assertThrows(InvalidTimeFormatException.class, () -> {
            parser.parse(":");
        });
    }

    @Test
    @DisplayName("Should throw exception when missing minute")
    public void testParseInvalidFormatMissingMinute() {
        assertThrows(InvalidTimeFormatException.class, () -> {
            parser.parse("14:");
        });
    }

    @Test
    @DisplayName("Should throw exception when missing hour")
    public void testParseInvalidFormatMissingHour() {
        assertThrows(InvalidTimeFormatException.class, () -> {
            parser.parse(":30");
        });
    }

    // Validation tests for hour-only format
    @Test
    @DisplayName("Should throw exception for hour-only >= 24")
    public void testParseHourOnlyInvalidTooLarge() {
        assertThrows(InvalidTimeException.class, () -> {
            parser.parse("24");
        });
    }

    @Test
    @DisplayName("Should throw exception for hour-only negative")
    public void testParseHourOnlyInvalidNegative() {
        assertThrows(InvalidTimeException.class, () -> {
            parser.parse("-5");
        });
    }

    @Test
    @DisplayName("Should throw exception for hour-only non-numeric")
    public void testParseHourOnlyInvalidLetters() {
        assertThrows(InvalidTimeFormatException.class, () -> {
            parser.parse("abc");
        });
    }
}
