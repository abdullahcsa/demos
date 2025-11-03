package com.abd.demo.service;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GeneralTimeConverter Tests")
public class GeneralTimeConverterTest {

    private final TimeToWordsConverter converter = new GeneralTimeConverter();

    @Test
    @DisplayName("Should convert single digit hour and minute")
    public void testConvertSingleDigitHourAndMinute() {
        Time time = new Time(6, 5);
        assertEquals("six five", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 6:32 to 'six thirty two'")
    public void testConvertSixThirtyTwo() {
        Time time = new Time(6, 32);
        assertEquals("six thirty two", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 14:30 to 'fourteen thirty'")
    public void testConvertFourteenThirty() {
        Time time = new Time(14, 30);
        assertEquals("fourteen thirty", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 0:00 to 'zero zero'")
    public void testConvertMidnight() {
        Time time = new Time(0, 0);
        assertEquals("zero zero", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 23:59 to 'twenty three fifty nine'")
    public void testConvertEndOfDay() {
        Time time = new Time(23, 59);
        assertEquals("twenty three fifty nine", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 12:00 to 'twelve zero'")
    public void testConvertNoon() {
        Time time = new Time(12, 0);
        assertEquals("twelve zero", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 9:15 to 'nine fifteen'")
    public void testConvertNineFifteen() {
        Time time = new Time(9, 15);
        assertEquals("nine fifteen", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 17:45 to 'seventeen forty five'")
    public void testConvertSeventeenFortyFive() {
        Time time = new Time(17, 45);
        assertEquals("seventeen forty five", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 1:01 to 'one one'")
    public void testConvertOneOhOne() {
        Time time = new Time(1, 1);
        assertEquals("one one", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 10:10 to 'ten ten'")
    public void testConvertTenTen() {
        Time time = new Time(10, 10);
        assertEquals("ten ten", converter.convert(time));
    }

    @Test
    @DisplayName("Should convert 20:20 to 'twenty twenty'")
    public void testConvertTwentyTwenty() {
        Time time = new Time(20, 20);
        assertEquals("twenty twenty", converter.convert(time));
    }

    @Test
    @DisplayName("Should handle all times (canHandle should return true)")
    public void testCanHandleAnyTime() {
        Time time = new Time(6, 32);
        assertTrue(converter.canHandle(time));
    }
}
