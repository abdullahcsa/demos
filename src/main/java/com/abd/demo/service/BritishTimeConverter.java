package com.abd.demo.service;

import com.abd.demo.domain.Time;
import com.abd.demo.util.NumberToWordsUtil;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * British English time converter for times with minutes divisible by 5.
 * Handles British-specific phrases like "quarter past", "half past", "midnight", "noon", "o'clock".
 *
 * Fully functional approach using strategy pattern:
 * - Map-based handlers for special minute values (0, 15, 30, 45, etc.)
 * - Chain of responsibility for hour-based special cases (midnight, noon)
 * - Predicate-based selection for "past" vs "to" patterns
 *
 * No if-else statements - pure functional composition.
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

    // Hour-based special cases (chain of responsibility)
    private final List<ConditionalHandler> hourHandlers;

    // Generic minute handlers (past vs to)
    private final List<ConditionalHandler> genericHandlers;

    public BritishTimeConverter() {
        this.minuteHandlers = new HashMap<>();
        this.hourHandlers = new ArrayList<>();
        this.genericHandlers = new ArrayList<>();
        initializeHandlers();
    }

    /**
     * Initialize all handlers using functional approach.
     * No if-else statements - just declarative configuration.
     */
    private void initializeHandlers() {
        // Hour-based handlers for minute=0 (chain of responsibility)
        hourHandlers.add(new ConditionalHandler(
            time -> time.getHours() == MIDNIGHT_HOUR,
            time -> MIDNIGHT
        ));
        hourHandlers.add(new ConditionalHandler(
            time -> time.getHours() == NOON_HOUR,
            time -> NOON
        ));
        hourHandlers.add(new ConditionalHandler(
            time -> true, // default case
            time -> getHourInWords(time) + O_CLOCK
        ));

        // Special minute handlers
        minuteHandlers.put(0, time -> applyFirstMatching(hourHandlers, time));
        minuteHandlers.put(15, time -> QUARTER_PAST + getHourInWords(time));
        minuteHandlers.put(30, time -> HALF_PAST + getHourInWords(time));
        minuteHandlers.put(45, time -> QUARTER_TO + getNextHourInWords(time));

        // Generic handlers for "past" and "to" patterns
        genericHandlers.add(new ConditionalHandler(
            time -> time.getMinutes() < HALF_HOUR,
            time -> getMinutesInWords(time) + PAST + getHourInWords(time)
        ));
        genericHandlers.add(new ConditionalHandler(
            time -> true, // default case (minutes >= HALF_HOUR)
            time -> getMinutesToNextHourInWords(time) + TO + getNextHourInWords(time)
        ));
    }

    @Override
    public boolean canHandle(Time time) {
        return time.getMinutes() % MINUTES_DIVISOR == 0;
    }

    @Override
    public String convert(Time time) {
        return Optional.ofNullable(minuteHandlers.get(time.getMinutes()))
            .map(handler -> handler.apply(time))
            .orElseGet(() -> applyFirstMatching(genericHandlers, time));
    }

    /**
     * Apply the first matching conditional handler from the list.
     * Implements chain of responsibility pattern functionally.
     */
    private String applyFirstMatching(List<ConditionalHandler> handlers, Time time) {
        return handlers.stream()
            .filter(handler -> handler.test(time))
            .findFirst()
            .map(handler -> handler.apply(time))
            .orElseThrow(() -> new IllegalStateException("No handler matched"));
    }

    /**
     * Conditional handler combining predicate and function.
     * Enables chain of responsibility without if-else.
     */
    private static class ConditionalHandler {
        private final Predicate<Time> condition;
        private final Function<Time, String> handler;

        ConditionalHandler(Predicate<Time> condition, Function<Time, String> handler) {
            this.condition = condition;
            this.handler = handler;
        }

        boolean test(Time time) {
            return condition.test(time);
        }

        String apply(Time time) {
            return handler.apply(time);
        }
    }

    /**
     * Converts the hour component of time to words.
     * Converts from 24-hour to 12-hour format.
     */
    private String getHourInWords(Time time) {
        return NumberToWordsUtil.convert(to12HourFormat(time.getHours()));
    }

    /**
     * Converts the next hour to words (with rollover at midnight).
     * Converts from 24-hour to 12-hour format.
     */
    private String getNextHourInWords(Time time) {
        int nextHour = (time.getHours() + 1) % HOURS_IN_DAY;
        return NumberToWordsUtil.convert(to12HourFormat(nextHour));
    }

    /**
     * Converts 24-hour format to 12-hour format.
     * 0 stays 0 (for midnight), 12 stays 12 (for noon), 13-23 become 1-11.
     */
    private int to12HourFormat(int hour24) {
        return hour24 == 0 || hour24 == 12 ? hour24 : hour24 % 12;
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