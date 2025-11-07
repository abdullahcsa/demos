package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinutesToRuleTest {

    private MinutesToRule minutesToRule;

    @BeforeEach
    void setUp() {
        minutesToRule = new MinutesToRule();
    }

    @Test
    void canHandle_shouldReturnTrue_forMinutesGreaterThanThirty() {
        assertTrue(minutesToRule.canHandle(new Time(1, 35)));
        assertTrue(minutesToRule.canHandle(new Time(12, 40)));
        assertTrue(minutesToRule.canHandle(new Time(23, 55)));
    }

    @Test
    void canHandle_shouldReturnFalse_forThirtyOrLessMinutes() {
        assertFalse(minutesToRule.canHandle(new Time(1, 0)));
        assertFalse(minutesToRule.canHandle(new Time(12, 15)));
        assertFalse(minutesToRule.canHandle(new Time(23, 30)));
    }

    @Test
    void convert_shouldReturnMinutesToFormat() {
        assertEquals("twenty five to two", minutesToRule.convert(new Time(1, 35)));
        assertEquals("twenty to one", minutesToRule.convert(new Time(12, 40)));
        assertEquals("five to zero", minutesToRule.convert(new Time(23, 55)));
    }

    @Test
    void convert_shouldConvertTo12HourFormat() {
        assertEquals("twenty five to two", minutesToRule.convert(new Time(13, 35)));
        assertEquals("ten to zero", minutesToRule.convert(new Time(23, 50)));
    }

    @Test
    void convert_shouldHandleMidnightRollover() {
        assertEquals("twenty five to one", minutesToRule.convert(new Time(0, 35)));
    }

    @Test
    void getPriority_shouldReturn21() {
        assertEquals(21, minutesToRule.getPriority());
    }
}
