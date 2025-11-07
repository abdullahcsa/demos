package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import lombok.extern.slf4j.Slf4j;

/**
 * Rule for midnight (00:00).
 * Single Responsibility: Handle midnight time conversion.
 */
@Slf4j
public class MidnightRule implements TimeConversionRule {
    private static final int MIDNIGHT_HOUR = 0;
    private static final int MIDNIGHT_MINUTE = 0;
    private static final String MIDNIGHT = "midnight";

    @Override
    public boolean canHandle(Time time) {
        boolean canHandle = time.getHours() == MIDNIGHT_HOUR && time.getMinutes() == MIDNIGHT_MINUTE;
        log.trace("MidnightRule.canHandle({}) = {}", time, canHandle);
        return canHandle;
    }

    @Override
    public String convert(Time time) {
        log.debug("Converting {} to midnight", time);
        return MIDNIGHT;
    }

    @Override
    public int getPriority() {
        return 1; // Highest priority - very specific case
    }
}
