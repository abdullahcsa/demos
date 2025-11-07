package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuarterPastRuleTest {

    private QuarterPastRule quarterPastRule;

    @BeforeEach
    void setUp() {
        quarterPastRule = new QuarterPastRule();
    }

    @Test
    void canHandle_shouldReturnTrue_forFifteenMinutes() {
        assertTrue(quarterPastRule.canHandle(new Time(1, 15)));
        assertTrue(quarterPastRule.canHandle(new Time(12, 15)));
        assertTrue(quarterPastRule.canHandle(new Time(23, 15)));
    }

    @Test
    void canHandle_shouldReturnFalse_forNonFifteenMinutes() {
        assertFalse(quarterPastRule.canHandle(new Time(1, 0)));
        assertFalse(quarterPastRule.canHandle(new Time(12, 30)));
        assertFalse(quarterPastRule.canHandle(new Time(23, 45)));
    }

    @Test
    void convert_shouldReturnQuarterPastFormat() {
        assertEquals("quarter past one", quarterPastRule.convert(new Time(1, 15)));
        assertEquals("quarter past twelve", quarterPastRule.convert(new Time(12, 15)));
    }

    @Test
    void convert_shouldConvertTo12HourFormat() {
        assertEquals("quarter past one", quarterPastRule.convert(new Time(13, 15)));
        assertEquals("quarter past eleven", quarterPastRule.convert(new Time(23, 15)));
    }

    @Test
    void getPriority_shouldReturn10() {
        assertEquals(10, quarterPastRule.getPriority());
    }
}
