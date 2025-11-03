package com.abd.demo.util;

public class NumberToWordsUtil {

    private static final String[] ONES = {
        "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    };

    private static final String[] TEENS = {
        "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen",
        "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS = {
        "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    private NumberToWordsUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String convert(int number) {
        if (number == 0) {
            return ONES[0];
        }

        if (number < 0) {
            return "minus " + convert(-number);
        }

        if (number < 10) {
            return ONES[number];
        }

        if (number < 20) {
            return TEENS[number - 10];
        }

        if (number < 100) {
            int tensDigit = number / 10;
            int onesDigit = number % 10;

            if (onesDigit == 0) {
                return TENS[tensDigit];
            }

            return TENS[tensDigit] + " " + ONES[onesDigit];
        }

        throw new IllegalArgumentException("Number out of range: " + number);
    }

    public static String convert(String numberStr) {
        if (numberStr == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        String trimmed = numberStr.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }

        try {
            int number = Integer.parseInt(trimmed);
            return convert(number);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid number format: " + numberStr);
        }
    }
}
