package com.abd.demo.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NumberToWordsUtil {

    // Immutable map containing all numbers 0-99 mapped to their word representations
    private static final Map<Integer, String> NUMBER_TO_WORDS = createNumberMap();

    private NumberToWordsUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static Map<Integer, String> createNumberMap() {
        String[] ones = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String[] teens = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen",
                         "sixteen", "seventeen", "eighteen", "nineteen"};
        String[] tens = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

        Map<Integer, String> map = new HashMap<>();

        // 0-9
        for (int i = 0; i < 10; i++) {
            map.put(i, ones[i]);
        }

        // 10-19
        for (int i = 10; i < 20; i++) {
            map.put(i, teens[i - 10]);
        }

        // 20-99
        for (int i = 20; i < 100; i++) {
            int tensDigit = i / 10;
            int onesDigit = i % 10;
            map.put(i, onesDigit == 0 ? tens[tensDigit] : tens[tensDigit] + " " + ones[onesDigit]);
        }

        return Map.copyOf(map);
    }

    public static String convert(int number) {
        return Optional.of(number)
            .map(n -> n < 0 ? "minus " + convert(-n) : NUMBER_TO_WORDS.get(n))
            .orElseThrow(() -> new IllegalArgumentException("Number out of range: " + number));
    }

    public static String convert(String numberStr) {
        return Optional.ofNullable(numberStr)
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(s -> {
                try {
                    return convert(Integer.parseInt(s));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid number format: " + numberStr);
                }
            })
            .orElseThrow(() -> new IllegalArgumentException(
                Optional.ofNullable(numberStr).isPresent() ? "Input cannot be empty" : "Input cannot be null"
            ));
    }
}
