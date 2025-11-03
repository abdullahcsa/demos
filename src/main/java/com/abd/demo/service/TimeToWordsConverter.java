package com.abd.demo.service;

import com.abd.demo.domain.Time;

/**
 * Strategy interface for converting Time to words.
 * Allows different conversion strategies (general, special cases, etc.)
 */
public interface TimeToWordsConverter {
    /**
     * Converts a Time object to its word representation.
     * @param time the time to convert
     * @return the time in words
     */
    String convert(Time time);

    /**
     * Checks if this converter can handle the given time.
     * Useful for chain of responsibility pattern with special cases.
     * @param time the time to check
     * @return true if this converter can handle the time
     */
    default boolean canHandle(Time time) {
        return true; // General converter can handle any time
    }
}
