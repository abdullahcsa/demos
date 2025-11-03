package com.abd.demo.service;

import com.abd.demo.domain.Time;
import com.abd.demo.util.NumberToWordsUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * British English time converter for times with minutes divisible by 5.
 * Handles British-specific phrases like "quarter past", "half past", "midnight", "noon", "o'clock".
 *
 * Uses a hybrid approach:
 * - Map-based handlers for special minute values (0, 15, 30, 45, etc.)
 * - Generic handlers for "past" and "to" patterns
 *
 * To add new special cases, simply add entries to the minuteHandlers map.
 * No modification to the convert() method needed.
 */
public class BritishTimeConverter implements TimeToWordsConverter {

    private static final int MINUTES_DIVISOR = 5;
    private static final int HALF_HOUR = 30;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_DAY = 24;

    // British English time phrase constants
    private static final String MIDNIGHT = "midnight";
    private static final String NOON = "noon";
    private static final String O_CLOCK = " o'clock";
    private static final String QUARTER_PAST = "quarter past ";
    private static final String HALF_PAST = "half past ";
    private static final String QUARTER_TO = "quarter to ";
    private static final String PAST = " past ";
    private static final String TO = " to ";

    // Special hour values
    private static final int MIDNIGHT_HOUR = 0;
    private static final int NOON_HOUR = 12;

    // Special handlers for specific minute values
    private final Map<Integer, Function<Time, String>> minuteHandlers;

    public BritishTimeConverter() {
        this.minuteHandlers = new HashMap<>();
        initializeMinuteHandlers();
    }

    /**
     * Initialize special handlers for specific minute values.
     * Add new special cases here without modifying convert() method.
     */
    private void initializeMinuteHandlers() {
        // On the hour - special handling for midnight, noon, and o'clock
        minuteHandlers.put(0, time -> {
            int hour = time.getHours();
            if (hour == MIDNIGHT_HOUR) {
                return MIDNIGHT;
            } else if (hour == NOON_HOUR) {
                return NOON;
            } else {
                return getHourInWords(time) + O_CLOCK;
            }
        });

        // Quarter past
        minuteHandlers.put(15, time -> QUARTER_PAST + getHourInWords(time));

        // Half past
        minuteHandlers.put(30, time -> HALF_PAST + getHourInWords(time));

        // Quarter to
        minuteHandlers.put(45, time -> QUARTER_TO + getNextHourInWords(time));

        // Future special cases can be added here, for example:
        // minuteHandlers.put(20, time -> "twenty" + PAST + getHourInWords(time));
        // minuteHandlers.put(40, time -> "twenty" + TO + getNextHourInWords(time));
    }

    @Override
    public boolean canHandle(Time time) {
        return time.getMinutes() % MINUTES_DIVISOR == 0;
    }

    @Override
    public String convert(Time time) {
        int minutes = time.getMinutes();

        // Check if there's a special handler for this minute value
        if (minuteHandlers.containsKey(minutes)) {
            return minuteHandlers.get(minutes).apply(time);
        }

        // Fall back to generic "past" or "to" conversion
        return convertWithPastOrTo(time);
    }

    /**
     * Generic conversion for "past" (1-30) or "to" (31-59) patterns.
     * This method handles all cases not covered by special handlers.
     */
    private String convertWithPastOrTo(Time time) {
        int minutes = time.getMinutes();

        if (minutes < HALF_HOUR) {
            // Past pattern
            String minutesInWords = getMinutesInWords(time);
            return minutesInWords + PAST + getHourInWords(time);
        } else {
            // To pattern
            String minutesInWords = getMinutesToNextHourInWords(time);
            return minutesInWords + TO + getNextHourInWords(time);
        }
    }

    /**
     * Converts the hour component of time to words.
     */
    private String getHourInWords(Time time) {
        return NumberToWordsUtil.convert(time.getHours());
    }

    /**
     * Converts the next hour to words (with rollover at midnight).
     */
    private String getNextHourInWords(Time time) {
        int nextHour = (time.getHours() + 1) % HOURS_IN_DAY;
        return NumberToWordsUtil.convert(nextHour);
    }

    /**
     * Converts the minutes component to words.
     */
    private String getMinutesInWords(Time time) {
        return NumberToWordsUtil.convert(time.getMinutes());
    }

    /**
     * Converts minutes remaining to next hour to words.
     */
    private String getMinutesToNextHourInWords(Time time) {
        int minutesToNextHour = MINUTES_IN_HOUR - time.getMinutes();
        return NumberToWordsUtil.convert(minutesToNextHour);
    }
}
