package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import com.abd.demo.util.NumberToWordsUtil;

/**
 * Rule for minutes to the next hour (for minutes 31-59, excluding special cases).
 * Single Responsibility: Handle "X minutes to Y" time conversion.
 */
public class MinutesToRule implements TimeConversionRule {
    private static final int HALF_HOUR = 30;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_DAY = 24;
    private static final String TO = " to ";
    private static final int HOURS_IN_HALF_DAY = 12;

    @Override
    public boolean canHandle(Time time) {
        return time.getMinutes() > HALF_HOUR;
    }

    @Override
    public String convert(Time time) {
        int minutesToNextHour = MINUTES_IN_HOUR - time.getMinutes();
        int nextHour = (time.getHours() + 1) % HOURS_IN_DAY;
        int nextHour12 = to12HourFormat(nextHour);
        return NumberToWordsUtil.convert(minutesToNextHour) + TO + NumberToWordsUtil.convert(nextHour12);
    }

    @Override
    public int getPriority() {
        return 21; // Lowest priority - generic fallback case
    }

    /**
     * Converts 24-hour format to 12-hour format.
     */
    private int to12HourFormat(int hour24) {
        return hour24 == 0 || hour24 == HOURS_IN_HALF_DAY ? hour24 : hour24 % HOURS_IN_HALF_DAY;
    }
}
