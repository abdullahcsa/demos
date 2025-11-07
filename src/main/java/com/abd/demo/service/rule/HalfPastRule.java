package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import com.abd.demo.util.NumberToWordsUtil;

/**
 * Rule for half past the hour (X:30).
 * Single Responsibility: Handle "half past" time conversion.
 */
public class HalfPastRule implements TimeConversionRule {
    private static final int HALF_PAST_MINUTE = 30;
    private static final String HALF_PAST = "half past ";
    private static final int HOURS_IN_HALF_DAY = 12;

    @Override
    public boolean canHandle(Time time) {
        return time.getMinutes() == HALF_PAST_MINUTE;
    }

    @Override
    public String convert(Time time) {
        int hour12 = to12HourFormat(time.getHours());
        return HALF_PAST + NumberToWordsUtil.convert(hour12);
    }

    @Override
    public int getPriority() {
        return 11; // High priority - specific minute value
    }

    /**
     * Converts 24-hour format to 12-hour format.
     */
    private int to12HourFormat(int hour24) {
        return hour24 == 0 || hour24 == HOURS_IN_HALF_DAY ? hour24 : hour24 % HOURS_IN_HALF_DAY;
    }
}
