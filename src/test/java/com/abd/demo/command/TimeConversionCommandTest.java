package com.abd.demo.command;

import com.abd.demo.adapter.OutputAdapter;
import com.abd.demo.domain.Time;
import com.abd.demo.service.TimeConverterService;
import com.abd.demo.service.TimeParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimeConversionCommandTest {

    @Mock
    private TimeParser mockTimeParser;

    @Mock
    private TimeConverterService mockTimeConverterService;

    @Mock
    private OutputAdapter mockOutput;

    private TimeConversionCommand timeConversionCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        timeConversionCommand = new TimeConversionCommand(
            mockTimeParser,
            mockTimeConverterService,
            mockOutput
        );
    }

    @Test
    void canHandle_shouldAlwaysReturnTrue() {
        assertTrue(timeConversionCommand.canHandle("12:30"));
        assertTrue(timeConversionCommand.canHandle("help"));
        assertTrue(timeConversionCommand.canHandle("exit"));
        assertTrue(timeConversionCommand.canHandle("anything"));
        assertTrue(timeConversionCommand.canHandle(""));
    }

    @Test
    void execute_shouldParseConvertAndDisplay_whenValidTime() {
        String input = "12:30";
        Time mockTime = new Time(12, 30);
        String expectedOutput = "half past twelve";

        when(mockTimeParser.parse(input)).thenReturn(mockTime);
        when(mockTimeConverterService.convert(mockTime)).thenReturn(expectedOutput);

        CommandResult result = timeConversionCommand.execute(input);

        verify(mockTimeParser, times(1)).parse(input);
        verify(mockTimeConverterService, times(1)).convert(mockTime);
        verify(mockOutput, times(1)).showResult(expectedOutput);
        verify(mockOutput, never()).showError(anyString());

        assertFalse(result.shouldExit());
        assertTrue(result.shouldShowBlankLine());
    }

    @Test
    void execute_shouldShowError_whenParsingFails() {
        String input = "invalid";
        String errorMessage = "Invalid time format";

        when(mockTimeParser.parse(input)).thenThrow(new IllegalArgumentException(errorMessage));

        CommandResult result = timeConversionCommand.execute(input);

        verify(mockTimeParser, times(1)).parse(input);
        verify(mockTimeConverterService, never()).convert(any());
        verify(mockOutput, times(1)).showError(errorMessage);
        verify(mockOutput, never()).showResult(anyString());

        assertFalse(result.shouldExit());
        assertTrue(result.shouldShowBlankLine());
    }

    @Test
    void execute_shouldReturnContinueRunning() {
        String input = "12:30";
        Time mockTime = new Time(12, 30);

        when(mockTimeParser.parse(input)).thenReturn(mockTime);
        when(mockTimeConverterService.convert(mockTime)).thenReturn("half past twelve");

        CommandResult result = timeConversionCommand.execute(input);

        assertFalse(result.shouldExit());
        assertTrue(result.shouldShowBlankLine());
    }

    @Test
    void getPriority_shouldReturnMaxValue() {
        assertEquals(Integer.MAX_VALUE, timeConversionCommand.getPriority());
    }
}
