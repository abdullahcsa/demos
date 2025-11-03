package com.abd.demo.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Time Value Object Tests")
public class TimeTest {

    @Test
    @DisplayName("Should create valid time with hours and minutes")
    public void testValidTimeCreation() {
        Time time = new Time(14, 30);
        assertEquals(14, time.getHours());
        assertEquals(30, time.getMinutes());
    }

    @Test
    @DisplayName("Should create valid time with single digit hour")
    public void testValidTimeWithSingleDigitHour() {
        Time time = new Time(9, 45);
        assertEquals(9, time.getHours());
        assertEquals(45, time.getMinutes());
    }

    @Test
    @DisplayName("Should create valid time for midnight (00:00)")
    public void testValidTimeMidnight() {
        Time time = new Time(0, 0);
        assertEquals(0, time.getHours());
        assertEquals(0, time.getMinutes());
    }

    @Test
    @DisplayName("Should create valid time for end of day (23:59)")
    public void testValidTimeEndOfDay() {
        Time time = new Time(23, 59);
        assertEquals(23, time.getHours());
        assertEquals(59, time.getMinutes());
    }

    @Test
    @DisplayName("Should throw exception for negative hour")
    public void testInvalidHourNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Time(-1, 30);
        });
    }

    @Test
    @DisplayName("Should throw exception for hour >= 24")
    public void testInvalidHourTooLarge() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Time(24, 30);
        });
    }

    @Test
    @DisplayName("Should throw exception for negative minute")
    public void testInvalidMinuteNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Time(14, -1);
        });
    }

    @Test
    @DisplayName("Should throw exception for minute >= 60")
    public void testInvalidMinuteTooLarge() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Time(14, 60);
        });
    }

    @Test
    @DisplayName("Should be equal for same time values")
    public void testTimeEquality() {
        Time time1 = new Time(14, 30);
        Time time2 = new Time(14, 30);
        assertEquals(time1, time2);
    }

    @Test
    @DisplayName("Should not be equal for different time values")
    public void testTimeInequality() {
        Time time1 = new Time(14, 30);
        Time time2 = new Time(14, 31);
        assertNotEquals(time1, time2);
    }

    @Test
    @DisplayName("Should have same hash code for equal times")
    public void testTimeHashCode() {
        Time time1 = new Time(14, 30);
        Time time2 = new Time(14, 30);
        assertEquals(time1.hashCode(), time2.hashCode());
    }

    @Test
    @DisplayName("Should format time as HH:MM string")
    public void testTimeToString() {
        Time time = new Time(14, 30);
        String result = time.toString();
        assertTrue(result.contains("14"));
        assertTrue(result.contains("30"));
    }

    @Test
    @DisplayName("Should be immutable")
    public void testTimeImmutability() {
        Time time = new Time(14, 30);
        int originalHours = time.getHours();
        int originalMinutes = time.getMinutes();

        // Try to create new time - original should remain unchanged
        Time newTime = new Time(15, 45);

        assertEquals(originalHours, time.getHours());
        assertEquals(originalMinutes, time.getMinutes());
    }
}
