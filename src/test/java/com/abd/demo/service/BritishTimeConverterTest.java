package com.abd.demo.service;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BritishTimeConverterFunctional Tests - Functional Programming Version")
class BritishTimeConverterTest {

    private BritishTimeConverter converter;

    @BeforeEach
    void setUp() {
        converter = new BritishTimeConverter();
    }

    // Test canHandle
    @Test
    @DisplayName("Should handle times with minutes divisible by 5")
    void testCanHandleMultiplesOfFive() {
        assertTrue(converter.canHandle(new Time(2, 5)));
        assertTrue(converter.canHandle(new Time(6, 25)));
        assertTrue(converter.canHandle(new Time(8, 40)));
        assertTrue(converter.canHandle(new Time(11, 55)));
        assertTrue(converter.canHandle(new Time(12, 0)));
        assertTrue(converter.canHandle(new Time(3, 30)));
    }

    @Test
    @DisplayName("Should not handle times with minutes not divisible by 5")
    void testCannotHandleNonMultiplesOfFive() {
        assertFalse(converter.canHandle(new Time(6, 32)));
        assertFalse(converter.canHandle(new Time(14, 17)));
        assertFalse(converter.canHandle(new Time(9, 43)));
    }

    // Test special cases
    @Test
    @DisplayName("Should convert midnight")
    void testConvertMidnight() {
        assertEquals("midnight", converter.convert(new Time(0, 0)));
    }

    @Test
    @DisplayName("Should convert noon")
    void testConvertNoon() {
        assertEquals("noon", converter.convert(new Time(12, 0)));
    }

    // Test o'clock
    @Test
    @DisplayName("Should convert times on the hour to o'clock")
    void testConvertOClock() {
        assertEquals("one o'clock", converter.convert(new Time(1, 0)));
        assertEquals("five o'clock", converter.convert(new Time(5, 0)));
        assertEquals("eleven o'clock", converter.convert(new Time(11, 0)));
        assertEquals("one o'clock", converter.convert(new Time(13, 0)));
    }

    // Test quarter past
    @Test
    @DisplayName("Should convert quarter past times")
    void testConvertQuarterPast() {
        assertEquals("quarter past four", converter.convert(new Time(4, 15)));
        assertEquals("quarter past ten", converter.convert(new Time(10, 15)));
        assertEquals("quarter past two", converter.convert(new Time(14, 15)));
    }

    // Test half past
    @Test
    @DisplayName("Should convert half past times")
    void testConvertHalfPast() {
        assertEquals("half past three", converter.convert(new Time(3, 30)));
        assertEquals("half past seven", converter.convert(new Time(7, 30)));
        assertEquals("half past two", converter.convert(new Time(14, 30)));
    }

    // Test quarter to
    @Test
    @DisplayName("Should convert quarter to times")
    void testConvertQuarterTo() {
        assertEquals("quarter to ten", converter.convert(new Time(9, 45)));
        assertEquals("quarter to five", converter.convert(new Time(16, 45)));
        assertEquals("quarter to zero", converter.convert(new Time(23, 45)));
    }

    // Test "past" pattern
    @Test
    @DisplayName("Should convert past times")
    void testConvertPast() {
        assertEquals("five past two", converter.convert(new Time(2, 5)));
        assertEquals("ten past nine", converter.convert(new Time(9, 10)));
        assertEquals("twenty past two", converter.convert(new Time(14, 20)));
        assertEquals("twenty five past six", converter.convert(new Time(6, 25)));
    }

    // Test "to" pattern
    @Test
    @DisplayName("Should convert to times")
    void testConvertTo() {
        assertEquals("twenty five to four", converter.convert(new Time(3, 35)));
        assertEquals("twenty to nine", converter.convert(new Time(8, 40)));
        assertEquals("ten to eight", converter.convert(new Time(19, 50)));
        assertEquals("five to twelve", converter.convert(new Time(11, 55)));
    }

    // Test 12-hour format conversion
    @Test
    @DisplayName("Should convert 24-hour to 12-hour format")
    void test24HourTo12HourConversion() {
        // AM times
        assertEquals("one o'clock", converter.convert(new Time(1, 0)));
        assertEquals("eleven o'clock", converter.convert(new Time(11, 0)));

        // PM times
        assertEquals("one o'clock", converter.convert(new Time(13, 0)));
        assertEquals("eleven o'clock", converter.convert(new Time(23, 0)));

        // Noon and midnight
        assertEquals("midnight", converter.convert(new Time(0, 0)));
        assertEquals("noon", converter.convert(new Time(12, 0)));
    }

    // Test hour rollover
    @Test
    @DisplayName("Should handle hour rollover at midnight")
    void testHourRollover() {
        assertEquals("five to zero", converter.convert(new Time(23, 55)));
        assertEquals("twenty to zero", converter.convert(new Time(23, 40)));
        assertEquals("quarter to zero", converter.convert(new Time(23, 45)));
    }

    // Edge cases
    @Test
    @DisplayName("Should handle all hour values correctly")
    void testAllHours() {
        for (int hour = 0; hour < 24; hour++) {
            Time time = new Time(hour, 0);
            String result = converter.convert(time);
            assertNotNull(result);
            assertFalse(result.isEmpty());
        }
    }

    @Test
    @DisplayName("Should handle all valid minute values")
    void testAllValidMinutes() {
        for (int minute = 0; minute < 60; minute += 5) {
            Time time = new Time(12, minute);
            String result = converter.convert(time);
            assertNotNull(result);
            assertFalse(result.isEmpty());
        }
    }
}
