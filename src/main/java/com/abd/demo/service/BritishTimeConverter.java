package com.abd.demo.service;

import com.abd.demo.domain.Time;
import com.abd.demo.domain.exceptions.TimeConversionException;
import com.abd.demo.service.rule.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * British English time converter for times with minutes divisible by 5.
 * Handles British-specific phrases like "quarter past", "half past", "midnight", "noon", "o'clock".
 * Uses Strategy Pattern with separate rule classes:
 * - Each conversion rule is encapsulated in its own class
 * - Rules are evaluated by priority order
 * - Follows Open/Closed Principle - add new rules without modifying this class
 */
@Slf4j
public class BritishTimeConverter implements TimeToWordsConverter {

    private static final int MINUTES_DIVISOR = 5;
    private final List<TimeConversionRule> rules;

    public BritishTimeConverter() {
        log.info("Initializing BritishTimeConverter with conversion rules");
        this.rules = createRules();
        log.debug("Loaded {} conversion rules", rules.size());
    }

    /**
     * Creates and initializes all conversion rules, sorted by priority.
     * Lower priority numbers are evaluated first.
     */
    private List<TimeConversionRule> createRules() {
        return Stream.of(
            new MidnightRule(),
            new NoonRule(),
            new OClockRule(),
            new QuarterPastRule(),
            new HalfPastRule(),
            new QuarterToRule(),
            new MinutesPastRule(),
            new MinutesToRule()
        )
         .sorted(Comparator.comparingInt(TimeConversionRule::getPriority))
         .collect(Collectors.toList());
    }

    @Override
    public boolean canHandle(Time time) {
        boolean canHandle = time.getMinutes() % MINUTES_DIVISOR == 0;
        log.debug("canHandle({}) = {}", time, canHandle);
        return canHandle;
    }

    @Override
    public String convert(Time time) {
        log.debug("Converting time: {}", time);

        return rules.stream()
            .filter(rule -> {
                boolean matches = rule.canHandle(time);
                if (matches) {
                    log.debug("Rule {} matched for time {}", rule.getClass().getSimpleName(), time);
                }
                return matches;
            })
            .findFirst()
            .map(rule -> {
                String converted = rule.convert(time);
                log.info("Converted {} to '{}' using {}", time, converted, rule.getClass().getSimpleName());
                return converted;
            })
            .orElseThrow(() -> {
                log.error("No rule matched for time: {}", time);
                return new TimeConversionException("No rule matched for time: " + time);
            });
    }
}