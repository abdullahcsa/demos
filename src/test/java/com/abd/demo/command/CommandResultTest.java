package com.abd.demo.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandResultTest {

    @Test
    void continueRunning_shouldReturnCorrectFlags() {
        CommandResult result = CommandResult.continueRunning();

        assertFalse(result.shouldExit());
        assertTrue(result.shouldShowBlankLine());
    }

    @Test
    void exit_shouldReturnCorrectFlags() {
        CommandResult result = CommandResult.exit();

        assertTrue(result.shouldExit());
        assertFalse(result.shouldShowBlankLine());
    }

    @Test
    void continueWithoutBlankLine_shouldReturnCorrectFlags() {
        CommandResult result = CommandResult.continueWithoutBlankLine();

        assertFalse(result.shouldExit());
        assertFalse(result.shouldShowBlankLine());
    }
}
