package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import com.abd.demo.util.NumberToWordsUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Rule for minutes past the hour (for minutes 1-29, excluding special cases).
 * Single Responsibility: Handle "X minutes past Y" time conversion.
 */
@Slf4j
public class MinutesPastRule implements TimeConversionRule {
    private static final int HALF_HOUR = 30;
    private static final String PAST = " past ";
    private static final int HOURS_IN_HALF_DAY = 12;

    @Override
    public boolean canHandle(Time time) {
        boolean canHandle = time.getMinutes() > 0 && time.getMinutes() < HALF_HOUR;
        log.trace("MinutesPastRule.canHandle({}) = {}", time, canHandle);
        return canHandle;
    }

    @Override
    public String convert(Time time) {
        int hour12 = to12HourFormat(time.getHours());
        String result = NumberToWordsUtil.convert(time.getMinutes()) + PAST + NumberToWordsUtil.convert(hour12);
        log.debug("Converting {} to '{}'", time, result);
        return result;
    }

    @Override
    public int getPriority() {
        return 20; // Lower priority - generic case
    }

    /**
     * Converts 24-hour format to 12-hour format.
     */
    private int to12HourFormat(int hour24) {
        return hour24 == 0 || hour24 == HOURS_IN_HALF_DAY ? hour24 : hour24 % HOURS_IN_HALF_DAY;
    }
}
