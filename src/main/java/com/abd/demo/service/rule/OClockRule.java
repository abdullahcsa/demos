package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import com.abd.demo.util.NumberToWordsUtil;

/**
 * Rule for times on the hour (X:00).
 * Single Responsibility: Handle "o'clock" time conversion.
 */
public class OClockRule implements TimeConversionRule {
    private static final int ON_THE_HOUR_MINUTE = 0;
    private static final String O_CLOCK = " o'clock";
    private static final int HOURS_IN_HALF_DAY = 12;

    @Override
    public boolean canHandle(Time time) {
        return time.getMinutes() == ON_THE_HOUR_MINUTE;
    }

    @Override
    public String convert(Time time) {
        int hour12 = to12HourFormat(time.getHours());
        return NumberToWordsUtil.convert(hour12) + O_CLOCK;
    }

    @Override
    public int getPriority() {
        return 3; // High priority - specific minute value
    }

    /**
     * Converts 24-hour format to 12-hour format.
     * 0 stays 0 (for midnight), 12 stays 12 (for noon), 13-23 become 1-11.
     */
    private int to12HourFormat(int hour24) {
        return hour24 == 0 || hour24 == HOURS_IN_HALF_DAY ? hour24 : hour24 % HOURS_IN_HALF_DAY;
    }
}
