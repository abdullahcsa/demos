package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;

/**
 * Rule for noon (12:00).
 * Single Responsibility: Handle noon time conversion.
 */
public class NoonRule implements TimeConversionRule {
    private static final int NOON_HOUR = 12;
    private static final int NOON_MINUTE = 0;
    private static final String NOON = "noon";

    @Override
    public boolean canHandle(Time time) {
        return time.getHours() == NOON_HOUR && time.getMinutes() == NOON_MINUTE;
    }

    @Override
    public String convert(Time time) {
        return NOON;
    }

    @Override
    public int getPriority() {
        return 2; // High priority - very specific case
    }
}
