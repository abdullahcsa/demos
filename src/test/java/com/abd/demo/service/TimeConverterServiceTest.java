package com.abd.demo.service;

import com.abd.demo.domain.Time;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TimeConverterService Tests - Strategy Orchestration")
public class TimeConverterServiceTest {

    private final TimeConverterService service = new TimeConverterService();

    // Test special cases (multiples of 5) - Uses BritishTimeConverter by default
    @Test
    @DisplayName("Should use BritishTimeConverter for 2:05")
    public void testBritishConverterForFivePastTwo() {
        Time time = new Time(2, 5);
        assertEquals("five past two", service.convert(time));
    }

    @Test
    @DisplayName("Should use BritishTimeConverter for 6:25")
    public void testBritishConverterForTwentyFivePastSix() {
        Time time = new Time(6, 25);
        assertEquals("twenty five past six", service.convert(time));
    }

    @Test
    @DisplayName("Should use BritishTimeConverter for 8:40")
    public void testBritishConverterForTwentyToNine() {
        Time time = new Time(8, 40);
        assertEquals("twenty to nine", service.convert(time));
    }

    @Test
    @DisplayName("Should use BritishTimeConverter for 11:55")
    public void testBritishConverterForFiveToTwelve() {
        Time time = new Time(11, 55);
        assertEquals("five to twelve", service.convert(time));
    }

    // Test general cases (not multiples of 5)
    @Test
    @DisplayName("Should use GeneralTimeConverter for 6:32")
    public void testGeneralConverterForSixThirtyTwo() {
        Time time = new Time(6, 32);
        assertEquals("six thirty two", service.convert(time));
    }

    @Test
    @DisplayName("Should use GeneralTimeConverter for 14:17")
    public void testGeneralConverterForFourteenSeventeen() {
        Time time = new Time(14, 17);
        assertEquals("fourteen seventeen", service.convert(time));
    }

    @Test
    @DisplayName("Should use GeneralTimeConverter for 9:43")
    public void testGeneralConverterForNineFortyThree() {
        Time time = new Time(9, 43);
        assertEquals("nine forty three", service.convert(time));
    }

    // Test that service handles null gracefully
    @Test
    @DisplayName("Should throw exception for null time")
    public void testNullTime() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.convert(null);
        });
    }
}
