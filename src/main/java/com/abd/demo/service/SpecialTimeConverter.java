package com.abd.demo.service;

import com.abd.demo.domain.Time;
import com.abd.demo.util.NumberToWordsUtil;

/**
 * Special time converter for times with minutes divisible by 5.
 * Converts using British English conventions:
 * - Minutes 1-30: "X past Y" (e.g., 2:05 → "five past two")
 * - Minutes 31-59: "X to Y+1" (e.g., 8:40 → "twenty to nine")
 * - On the hour (00): "X zero" (can be enhanced to "X o'clock" later)
 */
public class SpecialTimeConverter implements TimeToWordsConverter {

    private static final int MINUTES_DIVISOR = 5;
    private static final int HALF_HOUR = 30;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_DAY = 24;

    @Override
    public boolean canHandle(Time time) {
        return time.getMinutes() % MINUTES_DIVISOR == 0;
    }

    @Override
    public String convert(Time time) {
        int hours = time.getHours();
        int minutes = time.getMinutes();

        // On the hour
        if (minutes == 0) {
            return NumberToWordsUtil.convert(hours) + " zero";
        }

        // Past conversions (minutes 1-30)
        if (minutes <= HALF_HOUR) {
            String minutesInWords = NumberToWordsUtil.convert(minutes);
            String hoursInWords = NumberToWordsUtil.convert(hours);
            return minutesInWords + " past " + hoursInWords;
        }

        // To conversions (minutes 31-59)
        int minutesToNextHour = MINUTES_IN_HOUR - minutes;
        int nextHour = (hours + 1) % HOURS_IN_DAY;

        String minutesInWords = NumberToWordsUtil.convert(minutesToNextHour);
        String hoursInWords = NumberToWordsUtil.convert(nextHour);

        return minutesInWords + " to " + hoursInWords;
    }
}
