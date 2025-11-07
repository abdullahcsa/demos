package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuarterToRuleTest {

    private QuarterToRule quarterToRule;

    @BeforeEach
    void setUp() {
        quarterToRule = new QuarterToRule();
    }

    @Test
    void canHandle_shouldReturnTrue_forFortyFiveMinutes() {
        assertTrue(quarterToRule.canHandle(new Time(1, 45)));
        assertTrue(quarterToRule.canHandle(new Time(12, 45)));
        assertTrue(quarterToRule.canHandle(new Time(23, 45)));
    }

    @Test
    void canHandle_shouldReturnFalse_forNonFortyFiveMinutes() {
        assertFalse(quarterToRule.canHandle(new Time(1, 0)));
        assertFalse(quarterToRule.canHandle(new Time(12, 15)));
        assertFalse(quarterToRule.canHandle(new Time(23, 30)));
    }

    @Test
    void convert_shouldReturnQuarterToFormat() {
        assertEquals("quarter to two", quarterToRule.convert(new Time(1, 45)));
        assertEquals("quarter to one", quarterToRule.convert(new Time(12, 45)));
    }

    @Test
    void convert_shouldConvertTo12HourFormat() {
        assertEquals("quarter to two", quarterToRule.convert(new Time(13, 45)));
        assertEquals("quarter to zero", quarterToRule.convert(new Time(23, 45)));
    }

    @Test
    void convert_shouldHandleMidnightRollover() {
        assertEquals("quarter to one", quarterToRule.convert(new Time(0, 45)));
    }

    @Test
    void getPriority_shouldReturn12() {
        assertEquals(12, quarterToRule.getPriority());
    }
}
