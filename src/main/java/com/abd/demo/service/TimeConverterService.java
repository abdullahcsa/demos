package com.abd.demo.service;

import com.abd.demo.domain.Time;

/**
 * Service that orchestrates different time conversion strategies.
 * Uses Chain of Responsibility pattern to select the appropriate converter.
 *
 * Supports multiple conversion styles:
 * - British (default): Uses BritishTimeConverter for British English phrases
 * - Other converters can be passed as arguments
 *
 * Conversion strategies (in order of precedence):
 * 1. BritishTimeConverter - for multiples of 5 (uses "past" and "to" format, British phrases)
 * 2. GeneralTimeConverter - fallback for all other times
 *
 * This design allows easy addition of more converters in the future.
 */
public class TimeConverterService {

    private final TimeToWordsConverter specialConverter;
    private final TimeToWordsConverter generalConverter;

    public TimeConverterService() {
        this.specialConverter = new BritishTimeConverter();
        this.generalConverter = new GeneralTimeConverter();
    }

    /**
     * Converts a Time object to its word representation using British English (default).
     * Selects the appropriate converter based on the time characteristics.
     *
     * @param time the time to convert
     * @return the time in words using British English
     * @throws IllegalArgumentException if time is null
     */
    public String convert(Time time) {
        return convert(time, specialConverter);
    }

    /**
     * Converts a Time object to its word representation using the specified converter.
     * Allows custom converters to be used instead of the default British converter.
     *
     * @param time the time to convert
     * @param specialConverter the converter to use for special cases (e.g., BritishTimeConverter)
     * @return the time in words using the specified converter
     * @throws IllegalArgumentException if time is null
     */
    public String convert(Time time, TimeToWordsConverter specialConverter) {
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be null");
        }

        // Try the provided special converter first
        if (specialConverter.canHandle(time)) {
            return specialConverter.convert(time);
        }

        // Fall back to general converter
        return generalConverter.convert(time);
    }
}
