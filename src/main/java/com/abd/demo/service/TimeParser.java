package com.abd.demo.service;

import com.abd.demo.domain.Time;

/**
 * Service for parsing time strings into Time value objects.
 * Accepts formats: "HH:MM" or "H:M"
 */
public class TimeParser {

    private static final String SEPARATOR = ":";

    public Time parse(String input) {
        validateInput(input);

        String trimmed = input.trim();
        validateFormat(trimmed);

        String[] parts = trimmed.split(SEPARATOR);
        validateParts(parts);

        try {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return new Time(hours, minutes);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid time format. Hours and minutes must be numbers.");
        }
    }

    private void validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Time input cannot be empty");
        }
    }

    private void validateFormat(String input) {
        if (!input.contains(SEPARATOR)) {
            throw new IllegalArgumentException("Invalid time format. Expected format: HH:MM or H:M");
        }

        long colonCount = input.chars().filter(ch -> ch == ':').count();
        if (colonCount != 1) {
            throw new IllegalArgumentException("Invalid time format. Expected exactly one colon separator");
        }
    }

    private void validateParts(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid time format. Expected format: HH:MM or H:M");
        }

        if (parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new IllegalArgumentException("Invalid time format. Both hours and minutes are required");
        }
    }
}
