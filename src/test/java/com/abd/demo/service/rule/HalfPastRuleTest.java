package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HalfPastRuleTest {

    private HalfPastRule halfPastRule;

    @BeforeEach
    void setUp() {
        halfPastRule = new HalfPastRule();
    }

    @Test
    void canHandle_shouldReturnTrue_forThirtyMinutes() {
        assertTrue(halfPastRule.canHandle(new Time(1, 30)));
        assertTrue(halfPastRule.canHandle(new Time(12, 30)));
        assertTrue(halfPastRule.canHandle(new Time(23, 30)));
    }

    @Test
    void canHandle_shouldReturnFalse_forNonThirtyMinutes() {
        assertFalse(halfPastRule.canHandle(new Time(1, 0)));
        assertFalse(halfPastRule.canHandle(new Time(12, 15)));
        assertFalse(halfPastRule.canHandle(new Time(23, 45)));
    }

    @Test
    void convert_shouldReturnHalfPastFormat() {
        assertEquals("half past one", halfPastRule.convert(new Time(1, 30)));
        assertEquals("half past twelve", halfPastRule.convert(new Time(12, 30)));
    }

    @Test
    void convert_shouldConvertTo12HourFormat() {
        assertEquals("half past one", halfPastRule.convert(new Time(13, 30)));
        assertEquals("half past eleven", halfPastRule.convert(new Time(23, 30)));
    }

    @Test
    void getPriority_shouldReturn11() {
        assertEquals(11, halfPastRule.getPriority());
    }
}
