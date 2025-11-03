package com.abd.demo.service;

import com.abd.demo.domain.Time;
import com.abd.demo.util.NumberToWordsUtil;

/**
 * General time converter that converts hours and minutes separately to words.
 * This is the basic conversion strategy.
 * Future special case converters can be added using the Strategy pattern.
 */
public class GeneralTimeConverter implements TimeToWordsConverter {

    @Override
    public String convert(Time time) {
        String hoursInWords = NumberToWordsUtil.convert(time.getHours());
        String minutesInWords = NumberToWordsUtil.convert(time.getMinutes());

        return hoursInWords + " " + minutesInWords;
    }

    @Override
    public boolean canHandle(Time time) {
        return true; // Can handle any time
    }
}
