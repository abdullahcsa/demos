package com.abd.demo.domain;

import com.abd.demo.domain.exceptions.InvalidTimeException;

import java.util.Objects;

/**
 * Value Object representing a time in 24-hour format.
 * Immutable and validated upon construction.
 */
public final class Time {
    private final int hours;
    private final int minutes;

    private static final int MIN_HOURS = 0;
    private static final int MAX_HOURS = 23;
    private static final int MIN_MINUTES = 0;
    private static final int MAX_MINUTES = 59;

    public Time(int hours, int minutes) {
        validateHours(hours);
        validateMinutes(minutes);
        this.hours = hours;
        this.minutes = minutes;
    }

    private void validateHours(int hours) {
        if (hours < MIN_HOURS || hours > MAX_HOURS) {
            throw new InvalidTimeException(
                String.format("Hours must be between %d and %d, got: %d",
                    MIN_HOURS, MAX_HOURS, hours)
            );
        }
    }

    private void validateMinutes(int minutes) {
        if (minutes < MIN_MINUTES || minutes > MAX_MINUTES) {
            throw new InvalidTimeException(
                String.format("Minutes must be between %d and %d, got: %d",
                    MIN_MINUTES, MAX_MINUTES, minutes)
            );
        }
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return hours == time.hours && minutes == time.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hours, minutes);
    }
}
