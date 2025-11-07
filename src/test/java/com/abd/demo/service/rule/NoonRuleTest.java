package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoonRuleTest {

    private NoonRule noonRule;

    @BeforeEach
    void setUp() {
        noonRule = new NoonRule();
    }

    @Test
    void canHandle_shouldReturnTrue_forNoon() {
        Time noon = new Time(12, 0);
        assertTrue(noonRule.canHandle(noon));
    }

    @Test
    void canHandle_shouldReturnFalse_forNonNoon() {
        assertFalse(noonRule.canHandle(new Time(12, 15)));
        assertFalse(noonRule.canHandle(new Time(0, 0)));
        assertFalse(noonRule.canHandle(new Time(13, 0)));
    }

    @Test
    void convert_shouldReturnNoon() {
        Time noon = new Time(12, 0);
        assertEquals("noon", noonRule.convert(noon));
    }

    @Test
    void getPriority_shouldReturn2() {
        assertEquals(2, noonRule.getPriority());
    }
}
