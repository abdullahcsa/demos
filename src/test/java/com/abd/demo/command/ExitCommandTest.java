package com.abd.demo.command;

import com.abd.demo.adapter.OutputAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExitCommandTest {

    @Mock
    private OutputAdapter mockOutput;

    private ExitCommand exitCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exitCommand = new ExitCommand(mockOutput);
    }

    @Test
    void canHandle_shouldReturnTrue_whenInputIsExit() {
        assertTrue(exitCommand.canHandle("exit"));
        assertTrue(exitCommand.canHandle("EXIT"));
        assertTrue(exitCommand.canHandle("Exit"));
    }

    @Test
    void canHandle_shouldReturnTrue_whenInputIsQuit() {
        assertTrue(exitCommand.canHandle("quit"));
        assertTrue(exitCommand.canHandle("QUIT"));
        assertTrue(exitCommand.canHandle("Quit"));
    }

    @Test
    void canHandle_shouldReturnTrue_whenInputIsQ() {
        assertTrue(exitCommand.canHandle("q"));
        assertTrue(exitCommand.canHandle("Q"));
    }

    @Test
    void canHandle_shouldReturnFalse_whenInputIsNotExitCommand() {
        assertFalse(exitCommand.canHandle("help"));
        assertFalse(exitCommand.canHandle("12:30"));
        assertFalse(exitCommand.canHandle(""));
        assertFalse(exitCommand.canHandle("exits"));
    }

    @Test
    void execute_shouldCallShowExit() {
        CommandResult result = exitCommand.execute("exit");

        verify(mockOutput, times(1)).showExit();
        assertNotNull(result);
    }

    @Test
    void execute_shouldReturnExit() {
        CommandResult result = exitCommand.execute("quit");

        assertTrue(result.shouldExit());
        assertFalse(result.shouldShowBlankLine());
    }

    @Test
    void getPriority_shouldReturn5() {
        assertEquals(5, exitCommand.getPriority());
    }
}
