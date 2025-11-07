package com.abd.demo.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@DisplayName("NumberToWordsUtil Tests")
public class NumberToWordsUtilTest {

    @Test
    @DisplayName("Should throw exception when trying to instantiate utility class")
    public void testUtilityClassConstructor() {
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<NumberToWordsUtil> constructor = NumberToWordsUtil.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    @DisplayName("Utility class constructor should throw IllegalStateException")
    public void testUtilityClassConstructorThrowsIllegalStateException() {
        try {
            Constructor<NumberToWordsUtil> constructor = NumberToWordsUtil.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
            fail("Should have thrown exception");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof IllegalStateException);
            assertEquals("Utility class", e.getCause().getMessage());
        }
    }

    @Test
    public void testConvertZero() {
        assertEquals("zero", NumberToWordsUtil.convert(0));
    }

    @Test
    public void testConvertSingleDigits() {
        assertEquals("one", NumberToWordsUtil.convert(1));
        assertEquals("two", NumberToWordsUtil.convert(2));
        assertEquals("three", NumberToWordsUtil.convert(3));
        assertEquals("four", NumberToWordsUtil.convert(4));
        assertEquals("five", NumberToWordsUtil.convert(5));
        assertEquals("six", NumberToWordsUtil.convert(6));
        assertEquals("seven", NumberToWordsUtil.convert(7));
        assertEquals("eight", NumberToWordsUtil.convert(8));
        assertEquals("nine", NumberToWordsUtil.convert(9));
    }

    @Test
    public void testConvertTeens() {
        assertEquals("ten", NumberToWordsUtil.convert(10));
        assertEquals("eleven", NumberToWordsUtil.convert(11));
        assertEquals("twelve", NumberToWordsUtil.convert(12));
        assertEquals("thirteen", NumberToWordsUtil.convert(13));
        assertEquals("fourteen", NumberToWordsUtil.convert(14));
        assertEquals("fifteen", NumberToWordsUtil.convert(15));
        assertEquals("sixteen", NumberToWordsUtil.convert(16));
        assertEquals("seventeen", NumberToWordsUtil.convert(17));
        assertEquals("eighteen", NumberToWordsUtil.convert(18));
        assertEquals("nineteen", NumberToWordsUtil.convert(19));
    }

    @Test
    public void testConvertTwenties() {
        assertEquals("twenty", NumberToWordsUtil.convert(20));
        assertEquals("twenty one", NumberToWordsUtil.convert(21));
        assertEquals("twenty five", NumberToWordsUtil.convert(25));
        assertEquals("twenty nine", NumberToWordsUtil.convert(29));
    }

    @Test
    public void testConvertThirties() {
        assertEquals("thirty", NumberToWordsUtil.convert(30));
        assertEquals("thirty three", NumberToWordsUtil.convert(33));
    }

    @Test
    public void testConvertForties() {
        assertEquals("forty", NumberToWordsUtil.convert(40));
        assertEquals("forty two", NumberToWordsUtil.convert(42));
    }

    @Test
    public void testConvertFifties() {
        assertEquals("fifty", NumberToWordsUtil.convert(50));
        assertEquals("fifty five", NumberToWordsUtil.convert(55));
    }

    @Test
    public void testConvertSixties() {
        assertEquals("sixty", NumberToWordsUtil.convert(60));
        assertEquals("sixty seven", NumberToWordsUtil.convert(67));
    }

    @Test
    public void testConvertSeventies() {
        assertEquals("seventy", NumberToWordsUtil.convert(70));
        assertEquals("seventy one", NumberToWordsUtil.convert(71));
    }

    @Test
    public void testConvertEighties() {
        assertEquals("eighty", NumberToWordsUtil.convert(80));
        assertEquals("eighty eight", NumberToWordsUtil.convert(88));
    }

    @Test
    public void testConvertNineties() {
        assertEquals("ninety", NumberToWordsUtil.convert(90));
        assertEquals("ninety nine", NumberToWordsUtil.convert(99));
    }

    @Test
    public void testConvertNegativeNumber() {
        assertEquals("minus five", NumberToWordsUtil.convert(-5));
        assertEquals("minus seventeen", NumberToWordsUtil.convert(-17));
        assertEquals("minus ninety nine", NumberToWordsUtil.convert(-99));
    }

    @Test
    public void testConvertStringWithValidNumber() {
        assertEquals("seventeen", NumberToWordsUtil.convert("17"));
        assertEquals("forty two", NumberToWordsUtil.convert("42"));
        assertEquals("zero", NumberToWordsUtil.convert("0"));
    }

    @Test
    public void testConvertStringWithInvalidNumber() {
        assertThrows(NumberFormatException.class, () -> {
            NumberToWordsUtil.convert("abc");
        });
    }

    @Test
    public void testConvertStringWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            NumberToWordsUtil.convert(null);
        });
    }

    @Test
    public void testConvertStringWithEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> {
            NumberToWordsUtil.convert("");
        });
    }

    @Test
    public void testConvertStringWithWhitespace() {
        assertEquals("seventeen", NumberToWordsUtil.convert("  17  "));
    }

    @Test
    public void testConvertStringWithNegativeNumber() {
        assertEquals("minus twenty three", NumberToWordsUtil.convert("-23"));
    }

    @Test
    public void testConvertNumberOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            NumberToWordsUtil.convert(100);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            NumberToWordsUtil.convert(1000);
        });
    }
}
