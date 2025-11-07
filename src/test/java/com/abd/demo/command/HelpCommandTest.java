package com.abd.demo.command;

import com.abd.demo.adapter.OutputAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HelpCommandTest {

    @Mock
    private OutputAdapter mockOutput;

    private HelpCommand helpCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        helpCommand = new HelpCommand(mockOutput);
    }

    @Test
    void canHandle_shouldReturnTrue_whenInputIsHelp() {
        assertTrue(helpCommand.canHandle("help"));
    }

    @Test
    void canHandle_shouldReturnTrue_whenInputIsHelpUpperCase() {
        assertTrue(helpCommand.canHandle("HELP"));
    }

    @Test
    void canHandle_shouldReturnTrue_whenInputIsHelpMixedCase() {
        assertTrue(helpCommand.canHandle("HeLp"));
    }

    @Test
    void canHandle_shouldReturnFalse_whenInputIsNotHelp() {
        assertFalse(helpCommand.canHandle("exit"));
        assertFalse(helpCommand.canHandle("12:30"));
        assertFalse(helpCommand.canHandle(""));
    }

    @Test
    void execute_shouldCallShowHelp() {
        CommandResult result = helpCommand.execute("help");

        verify(mockOutput, times(1)).showHelp();
        assertNotNull(result);
    }

    @Test
    void execute_shouldReturnContinueRunning() {
        CommandResult result = helpCommand.execute("help");

        assertFalse(result.shouldExit());
        assertTrue(result.shouldShowBlankLine());
    }

    @Test
    void getPriority_shouldReturn10() {
        assertEquals(10, helpCommand.getPriority());
    }
}
