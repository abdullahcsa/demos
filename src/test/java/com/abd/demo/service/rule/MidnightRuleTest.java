package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MidnightRuleTest {

    private MidnightRule midnightRule;

    @BeforeEach
    void setUp() {
        midnightRule = new MidnightRule();
    }

    @Test
    void canHandle_shouldReturnTrue_forMidnight() {
        Time midnight = new Time(0, 0);
        assertTrue(midnightRule.canHandle(midnight));
    }

    @Test
    void canHandle_shouldReturnFalse_forNonMidnight() {
        assertFalse(midnightRule.canHandle(new Time(0, 15)));
        assertFalse(midnightRule.canHandle(new Time(1, 0)));
        assertFalse(midnightRule.canHandle(new Time(12, 0)));
    }

    @Test
    void convert_shouldReturnMidnight() {
        Time midnight = new Time(0, 0);
        assertEquals("midnight", midnightRule.convert(midnight));
    }

    @Test
    void getPriority_shouldReturn1() {
        assertEquals(1, midnightRule.getPriority());
    }
}
