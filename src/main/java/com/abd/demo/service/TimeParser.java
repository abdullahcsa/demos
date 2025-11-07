package com.abd.demo.service;

import com.abd.demo.domain.Time;
import com.abd.demo.domain.exceptions.InvalidTimeFormatException;

/**
 * Service for parsing time strings into Time value objects.
 * Accepts formats:
 * - "HH:MM" or "H:M" (e.g., "07:30", "7:30")
 * - "H" or "HH" (e.g., "5", "12") - treated as hour on the hour (minutes = 00)
 */
public class TimeParser {

    private static final String SEPARATOR = ":";

    public Time parse(String input) {
        validateInput(input);

        String trimmed = input.trim();

        // Check if input contains colon separator
        if (trimmed.contains(SEPARATOR)) {
            return parseWithMinutes(trimmed);
        } else {
            return parseHourOnly(trimmed);
        }
    }

    /**
     * Parses time in "HH:MM" or "H:M" format.
     */
    private Time parseWithMinutes(String input) {
        validateFormat(input);

        String[] parts = input.split(SEPARATOR);
        validateParts(parts);

        try {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return new Time(hours, minutes);
        } catch (NumberFormatException e) {
            throw new InvalidTimeFormatException("Invalid time format. Hours and minutes must be numbers.", e);
        }
    }

    /**
     * Parses time in "H" or "HH" format (hour only).
     * Minutes default to 00.
     */
    private Time parseHourOnly(String input) {
        if (input.isEmpty()) {
            throw new InvalidTimeFormatException("Time input cannot be empty");
        }

        try {
            int hours = Integer.parseInt(input);
            return new Time(hours, 0);
        } catch (NumberFormatException e) {
            throw new InvalidTimeFormatException("Invalid time format. Hour must be a number.", e);
        }
    }

    private void validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidTimeFormatException("Time input cannot be empty");
        }
    }

    private void validateFormat(String input) {
        long colonCount = input.chars().filter(ch -> ch == ':').count();
        if (colonCount != 1) {
            throw new InvalidTimeFormatException("Invalid time format. Expected exactly one colon separator");
        }
    }

    private void validateParts(String[] parts) {
        if (parts.length != 2) {
            throw new InvalidTimeFormatException("Invalid time format. Expected format: HH:MM or H:M");
        }

        if (parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new InvalidTimeFormatException("Invalid time format. Both hours and minutes are required");
        }
    }
}
