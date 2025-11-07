package com.abd.demo.service.rule;

import com.abd.demo.domain.Time;

/**
 * Strategy interface for time conversion rules.
 * Each rule can check if it handles a specific time pattern and convert it.
 * Follows Strategy Pattern for clean separation of conversion logic.
 */
public interface TimeConversionRule {
    /**
     * Tests if this rule can handle the given time.
     *
     * @param time the time to check
     * @return true if this rule should handle the time
     */
    boolean canHandle(Time time);

    /**
     * Converts the time to British spoken format.
     *
     * @param time the time to convert
     * @return British spoken time string
     */
    String convert(Time time);

    /**
     * Returns the priority of this rule.
     * Lower numbers = higher priority.
     * Allows explicit ordering for rule evaluation.
     *
     * @return priority value (default: 100)
     */
    default int getPriority() {
        return 100;
    }
}
