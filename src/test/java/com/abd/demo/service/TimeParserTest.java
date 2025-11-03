package com.abd.demo.service;

import com.abd.demo.domain.Time;
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

    @Test
    @DisplayName("Should throw exception when no colon separator")
    public void testParseInvalidFormatNoColon() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("1430");
        });
    }

    @Test
    @DisplayName("Should throw exception for multiple colons")
    public void testParseInvalidFormatMultipleColons() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("14:30:00");
        });
    }

    @Test
    @DisplayName("Should throw exception for non-numeric input")
    public void testParseInvalidFormatLetters() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("ab:cd");
        });
    }

    @Test
    @DisplayName("Should throw exception for empty input")
    public void testParseInvalidFormatEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("");
        });
    }

    @Test
    @DisplayName("Should throw exception for null input")
    public void testParseInvalidFormatNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(null);
        });
    }

    @Test
    @DisplayName("Should throw exception for hour >= 24")
    public void testParseInvalidHourTooLarge() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("24:30");
        });
    }

    @Test
    @DisplayName("Should throw exception for minute >= 60")
    public void testParseInvalidMinuteTooLarge() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("14:60");
        });
    }

    @Test
    @DisplayName("Should throw exception for negative hour")
    public void testParseInvalidNegativeHour() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("-1:30");
        });
    }

    @Test
    @DisplayName("Should throw exception for negative minute")
    public void testParseInvalidNegativeMinute() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("14:-5");
        });
    }

    @Test
    @DisplayName("Should throw exception for only colon")
    public void testParseInvalidFormatOnlyColon() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(":");
        });
    }

    @Test
    @DisplayName("Should throw exception when missing minute")
    public void testParseInvalidFormatMissingMinute() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse("14:");
        });
    }

    @Test
    @DisplayName("Should throw exception when missing hour")
    public void testParseInvalidFormatMissingHour() {
        assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(":30");
        });
    }
}
