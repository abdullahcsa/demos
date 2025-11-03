package com.abd.demo.service;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BritishTimeConverter Tests - British English Time Conversion")
public class BritishTimeConverterTest {

    private final TimeToWordsConverter converter = new BritishTimeConverter();

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
    @DisplayName("Should convert 7:30 to 'half past seven'")
    public void testConvertHalfPastSeven() {
        Time time = new Time(7, 30);
        assertEquals("half past seven", converter.convert(time));
    }

    // Test quarter past (15 minutes)
    @Test
    @DisplayName("Should convert 4:15 to 'quarter past four'")
    public void testConvertQuarterPastFour() {
        Time time = new Time(4, 15);
        assertEquals("quarter past four", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 10:15 to 'quarter past ten'")
    public void testConvertQuarterPastTen() {
        Time time = new Time(10, 15);
        assertEquals("quarter past ten", converter.convert(time));
    }

    // Test half past (30 minutes)
    @Test
    @DisplayName("Should convert 3:30 to 'half past three'")
    public void testConvertHalfPastThree() {
        Time time = new Time(3, 30);
        assertEquals("half past three", converter.convert(time));
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
    @DisplayName("Should convert 16:45 to 'quarter to seventeen'")
    public void testConvertQuarterToSeventeen() {
        Time time = new Time(16, 45);
        assertEquals("quarter to seventeen", converter.convert(time));
    }

    // Test quarter to (45 minutes)
    @Test
    @DisplayName("Should convert 9:45 to 'quarter to ten'")
    public void testConvertQuarterToTen() {
        Time time = new Time(9, 45);
        assertEquals("quarter to ten", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 23:45 to 'quarter to zero' (hour rollover)")
    public void testConvertQuarterToZeroWithRollover() {
        Time time = new Time(23, 45);
        assertEquals("quarter to zero", converter.convert(time));
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

    // Test on the hour (00 minutes) - Special cases
    @Test
    @DisplayName("Should convert 0:00 to 'midnight'")
    public void testConvertMidnight() {
        Time time = new Time(0, 0);
        assertEquals("midnight", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 12:00 to 'noon'")
    public void testConvertNoon() {
        Time time = new Time(12, 0);
        assertEquals("noon", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 1:00 to 'one o'clock'")
    public void testConvertOneOClock() {
        Time time = new Time(1, 0);
        assertEquals("one o'clock", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 5:00 to 'five o'clock'")
    public void testConvertFiveOClock() {
        Time time = new Time(5, 0);
        assertEquals("five o'clock", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 23:00 to 'twenty three o'clock'")
    public void testConvertTwentyThreeOClock() {
        Time time = new Time(23, 0);
        assertEquals("twenty three o'clock", converter.convert(time));
    }
}
