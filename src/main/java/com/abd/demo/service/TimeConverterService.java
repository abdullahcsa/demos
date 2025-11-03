package com.abd.demo.service;

import com.abd.demo.domain.Time;

/**
 * Service that orchestrates different time conversion strategies.
 * Uses Chain of Responsibility pattern to select the appropriate converter.
 *
 * Conversion strategies (in order of precedence):
 * 1. SpecialTimeConverter - for multiples of 5 (uses "past" and "to" format)
 * 2. GeneralTimeConverter - fallback for all other times
 *
 * This design allows easy addition of more special cases in the future.
 */
public class TimeConverterService {

    private final TimeToWordsConverter specialConverter;
    private final TimeToWordsConverter generalConverter;

    public TimeConverterService() {
        this.specialConverter = new SpecialTimeConverter();
        this.generalConverter = new GeneralTimeConverter();
    }

    /**
     * Converts a Time object to its word representation.
     * Selects the appropriate converter based on the time characteristics.
     *
     * @param time the time to convert
     * @return the time in words
     * @throws IllegalArgumentException if time is null
     */
    public String convert(Time time) {
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be null");
        }

        // Try special converter first
        if (specialConverter.canHandle(time)) {
            return specialConverter.convert(time);
        }

        // Fall back to general converter
        return generalConverter.convert(time);
    }
}
