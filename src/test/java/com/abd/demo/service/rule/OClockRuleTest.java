package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OClockRuleTest {

    private OClockRule oClockRule;

    @BeforeEach
    void setUp() {
        oClockRule = new OClockRule();
    }

    @Test
    void canHandle_shouldReturnTrue_forOnTheHour() {
        assertTrue(oClockRule.canHandle(new Time(0, 0)));
        assertTrue(oClockRule.canHandle(new Time(1, 0)));
        assertTrue(oClockRule.canHandle(new Time(12, 0)));
        assertTrue(oClockRule.canHandle(new Time(23, 0)));
    }

    @Test
    void canHandle_shouldReturnFalse_forNonZeroMinutes() {
        assertFalse(oClockRule.canHandle(new Time(1, 15)));
        assertFalse(oClockRule.canHandle(new Time(12, 30)));
    }

    @Test
    void convert_shouldReturnOClockFormat() {
        assertEquals("one o'clock", oClockRule.convert(new Time(1, 0)));
        assertEquals("twelve o'clock", oClockRule.convert(new Time(12, 0)));
        assertEquals("zero o'clock", oClockRule.convert(new Time(0, 0)));
    }

    @Test
    void convert_shouldConvertTo12HourFormat() {
        assertEquals("one o'clock", oClockRule.convert(new Time(13, 0)));
        assertEquals("eleven o'clock", oClockRule.convert(new Time(23, 0)));
    }

    @Test
    void getPriority_shouldReturn3() {
        assertEquals(3, oClockRule.getPriority());
    }
}
