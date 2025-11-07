package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinutesPastRuleTest {

    private MinutesPastRule minutesPastRule;

    @BeforeEach
    void setUp() {
        minutesPastRule = new MinutesPastRule();
    }

    @Test
    void canHandle_shouldReturnTrue_forMinutesLessThanThirty() {
        assertTrue(minutesPastRule.canHandle(new Time(1, 5)));
        assertTrue(minutesPastRule.canHandle(new Time(12, 10)));
        assertTrue(minutesPastRule.canHandle(new Time(23, 25)));
    }

    @Test
    void canHandle_shouldReturnFalse_forZeroMinutes() {
        assertFalse(minutesPastRule.canHandle(new Time(1, 0)));
    }

    @Test
    void canHandle_shouldReturnFalse_forThirtyOrMoreMinutes() {
        assertFalse(minutesPastRule.canHandle(new Time(1, 30)));
        assertFalse(minutesPastRule.canHandle(new Time(12, 35)));
        assertFalse(minutesPastRule.canHandle(new Time(23, 45)));
    }

    @Test
    void convert_shouldReturnMinutesPastFormat() {
        assertEquals("five past one", minutesPastRule.convert(new Time(1, 5)));
        assertEquals("ten past twelve", minutesPastRule.convert(new Time(12, 10)));
        assertEquals("twenty five past eleven", minutesPastRule.convert(new Time(23, 25)));
    }

    @Test
    void convert_shouldConvertTo12HourFormat() {
        assertEquals("five past one", minutesPastRule.convert(new Time(13, 5)));
        assertEquals("twenty past eleven", minutesPastRule.convert(new Time(23, 20)));
    }

    @Test
    void getPriority_shouldReturn20() {
        assertEquals(20, minutesPastRule.getPriority());
    }
}
