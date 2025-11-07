package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;
import com.abd.demo.util.NumberToWordsUtil;

/**
 * Rule for quarter to the next hour (X:45).
 * Single Responsibility: Handle "quarter to" time conversion.
 */
public class QuarterToRule implements TimeConversionRule {
    private static final int QUARTER_TO_MINUTE = 45;
    private static final String QUARTER_TO = "quarter to ";
    private static final int HOURS_IN_HALF_DAY = 12;
    private static final int HOURS_IN_DAY = 24;

    @Override
    public boolean canHandle(Time time) {
        return time.getMinutes() == QUARTER_TO_MINUTE;
    }

    @Override
    public String convert(Time time) {
        int nextHour = (time.getHours() + 1) % HOURS_IN_DAY;
        int nextHour12 = to12HourFormat(nextHour);
        return QUARTER_TO + NumberToWordsUtil.convert(nextHour12);
    }

    @Override
    public int getPriority() {
        return 12; // High priority - specific minute value
    }

    /**
     * Converts 24-hour format to 12-hour format.
     */
    private int to12HourFormat(int hour24) {
        return hour24 == 0 || hour24 == HOURS_IN_HALF_DAY ? hour24 : hour24 % HOURS_IN_HALF_DAY;
    }
}
