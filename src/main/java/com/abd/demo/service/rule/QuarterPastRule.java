package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import com.abd.demo.util.NumberToWordsUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Rule for quarter past the hour (X:15).
 * Single Responsibility: Handle "quarter past" time conversion.
 */
@Slf4j
public class QuarterPastRule implements TimeConversionRule {
    private static final int QUARTER_PAST_MINUTE = 15;
    private static final String QUARTER_PAST = "quarter past ";
    private static final int HOURS_IN_HALF_DAY = 12;

    @Override
    public boolean canHandle(Time time) {
        boolean canHandle = time.getMinutes() == QUARTER_PAST_MINUTE;
        log.trace("QuarterPastRule.canHandle({}) = {}", time, canHandle);
        return canHandle;
    }

    @Override
    public String convert(Time time) {
        int hour12 = to12HourFormat(time.getHours());
        String result = QUARTER_PAST + NumberToWordsUtil.convert(hour12);
        log.debug("Converting {} to '{}'", time, result);
        return result;
    }

    @Override
    public int getPriority() {
        return 10; // High priority - specific minute value
    }

    /**
     * Converts 24-hour format to 12-hour format.
     */
    private int to12HourFormat(int hour24) {
        return hour24 == 0 || hour24 == HOURS_IN_HALF_DAY ? hour24 : hour24 % HOURS_IN_HALF_DAY;
    }
}
