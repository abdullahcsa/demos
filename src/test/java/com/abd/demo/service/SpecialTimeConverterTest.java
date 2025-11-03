package com.abd.demo.service;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SpecialTimeConverter Tests - Multiples of 5")
public class SpecialTimeConverterTest {

    private final TimeToWordsConverter converter = new SpecialTimeConverter();

    // Test canHandle - should only handle multiples of 5
    @Test
    @DisplayName("Should handle times with minutes divisible by 5")
    public void testCanHandleMultiplesOfFive() {
        assertTrue(converter.canHandle(new Time(2, 5)));
        assertTrue(converter.canHandle(new Time(6, 25)));
        assertTrue(converter.canHandle(new Time(8, 40)));
        assertTrue(converter.canHandle(new Time(11, 55)));
        assertTrue(converter.canHandle(new Time(12, 0)));
        assertTrue(converter.canHandle(new Time(3, 30)));
    }

    @Test
    @DisplayName("Should not handle times with minutes not divisible by 5")
    public void testCannotHandleNonMultiplesOfFive() {
        assertFalse(converter.canHandle(new Time(6, 32)));
        assertFalse(converter.canHandle(new Time(14, 17)));
        assertFalse(converter.canHandle(new Time(9, 43)));
    }

    // Test "past" conversions (minutes 5-30)
    @Test
    @DisplayName("Should convert 2:05 to 'five past two'")
    public void testConvertFivePastTwo() {
        Time time = new Time(2, 5);
        assertEquals("five past two", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 6:25 to 'twenty five past six'")
    public void testConvertTwentyFivePastSix() {
        Time time = new Time(6, 25);
        assertEquals("twenty five past six", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 9:10 to 'ten past nine'")
    public void testConvertTenPastNine() {
        Time time = new Time(9, 10);
        assertEquals("ten past nine", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 14:20 to 'twenty past fourteen'")
    public void testConvertTwentyPastFourteen() {
        Time time = new Time(14, 20);
        assertEquals("twenty past fourteen", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 7:30 to 'thirty past seven'")
    public void testConvertThirtyPastSeven() {
        Time time = new Time(7, 30);
        assertEquals("thirty past seven", converter.convert(time));
    }

    // Test "to" conversions (minutes 35-55)
    @Test
    @DisplayName("Should convert 8:40 to 'twenty to nine'")
    public void testConvertTwentyToNine() {
        Time time = new Time(8, 40);
        assertEquals("twenty to nine", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 11:55 to 'five to twelve'")
    public void testConvertFiveToTwelve() {
        Time time = new Time(11, 55);
        assertEquals("five to twelve", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 3:35 to 'twenty five to four'")
    public void testConvertTwentyFiveToFour() {
        Time time = new Time(3, 35);
        assertEquals("twenty five to four", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 16:45 to 'fifteen to seventeen'")
    public void testConvertFifteenToSeventeen() {
        Time time = new Time(16, 45);
        assertEquals("fifteen to seventeen", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 19:50 to 'ten to twenty'")
    public void testConvertTenToTwenty() {
        Time time = new Time(19, 50);
        assertEquals("ten to twenty", converter.convert(time));
    }

    // Test edge cases with hour rollover
    @Test
    @DisplayName("Should convert 23:55 to 'five to zero' (hour rollover)")
    public void testConvertFiveToZeroWithRollover() {
        Time time = new Time(23, 55);
        assertEquals("five to zero", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 23:40 to 'twenty to zero'")
    public void testConvertTwentyToZeroWithRollover() {
        Time time = new Time(23, 40);
        assertEquals("twenty to zero", converter.convert(time));
    }

    // Test on the hour (00 minutes)
    @Test
    @DisplayName("Should convert 5:00 to 'five zero'")
    public void testConvertFiveOClock() {
        Time time = new Time(5, 0);
        assertEquals("five zero", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 12:00 to 'twelve zero'")
    public void testConvertTwelveOClock() {
        Time time = new Time(12, 0);
        assertEquals("twelve zero", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 0:00 to 'zero zero'")
    public void testConvertMidnight() {
        Time time = new Time(0, 0);
        assertEquals("zero zero", converter.convert(time));
    }
}
