package com.abd.demo.integration;

import com.abd.demo.adapter.OutputAdapter;
import com.abd.demo.command.*;
import com.abd.demo.service.TimeConverterService;
import com.abd.demo.service.TimeParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for command processing flow.
 * Tests the interaction between Commands, Services, and Output handling.
 * Uses real implementations to verify command chain behavior.
 */
@DisplayName("Command Processing Integration Tests")
class CommandProcessingIntegrationTest {

    private TestOutputAdapter outputAdapter;
    private List<Command> commands;

    @BeforeEach
    void setUp() {
        outputAdapter = new TestOutputAdapter();
        TimeParser timeParser = new TimeParser();
        TimeConverterService timeConverterService = new TimeConverterService();

        commands = List.of(
            new ExitCommand(outputAdapter),
            new HelpCommand(outputAdapter),
            new ConfigCommand(outputAdapter),
            new TimeConversionCommand(timeParser, timeConverterService, outputAdapter)
        );
    }

    // ===== Command Selection Integration Tests =====

    @Test
    @DisplayName("Integration: 'help' should trigger HelpCommand")
    void shouldTriggerHelpCommand() {
        Command command = findCommandFor("help");
        assertNotNull(command);
        assertTrue(command instanceof HelpCommand);

        CommandResult result = command.execute("help");
        assertFalse(result.shouldExit());
        assertTrue(outputAdapter.helpCalled);
    }

    @Test
    @DisplayName("Integration: 'exit' should trigger ExitCommand")
    void shouldTriggerExitCommand() {
        Command command = findCommandFor("exit");
        assertNotNull(command);
        assertTrue(command instanceof ExitCommand);

        CommandResult result = command.execute("exit");
        assertTrue(result.shouldExit());
        assertTrue(outputAdapter.exitCalled);
    }

    @Test
    @DisplayName("Integration: 'config logs enable' should trigger ConfigCommand")
    void shouldTriggerConfigCommand() {
        Command command = findCommandFor("config logs enable");
        assertNotNull(command);
        assertTrue(command instanceof ConfigCommand);

        CommandResult result = command.execute("config logs enable");
        assertFalse(result.shouldExit());
        assertTrue(outputAdapter.successes.size() > 0);
    }

    @Test
    @DisplayName("Integration: Time input '12:30' should trigger TimeConversionCommand")
    void shouldTriggerTimeConversionCommand() {
        Command command = findCommandFor("12:30");
        assertNotNull(command);
        assertTrue(command instanceof TimeConversionCommand);

        CommandResult result = command.execute("12:30");
        assertFalse(result.shouldExit());
        assertEquals(1, outputAdapter.results.size());
        assertEquals("half past twelve", outputAdapter.results.get(0));
    }

    // ===== Command Priority Integration Tests =====

    @Test
    @DisplayName("Integration: ExitCommand should have highest priority")
    void exitCommandShouldHaveHighestPriority() {
        Command exitCmd = commands.stream()
            .filter(c -> c instanceof ExitCommand)
            .findFirst()
            .orElseThrow();

        assertTrue(commands.stream()
            .filter(c -> !(c instanceof ExitCommand))
            .allMatch(c -> exitCmd.getPriority() < c.getPriority())
        );
    }

    @Test
    @DisplayName("Integration: TimeConversionCommand should be fallback (lowest priority)")
    void timeConversionCommandShouldBeFallback() {
        Command timeCmd = commands.stream()
            .filter(c -> c instanceof TimeConversionCommand)
            .findFirst()
            .orElseThrow();

        assertTrue(commands.stream()
            .filter(c -> !(c instanceof TimeConversionCommand))
            .allMatch(c -> c.getPriority() < timeCmd.getPriority())
        );
    }

    // ===== Command Flow Integration Tests =====

    @Test
    @DisplayName("Integration: Multiple commands in sequence should work independently")
    void shouldExecuteMultipleCommandsIndependently() {
        // Execute help
        Command helpCmd = findCommandFor("help");
        CommandResult result1 = helpCmd.execute("help");
        assertFalse(result1.shouldExit());

        // Execute time conversion
        Command timeCmd = findCommandFor("14:15");
        CommandResult result2 = timeCmd.execute("14:15");
        assertFalse(result2.shouldExit());

        // Execute config
        Command configCmd = findCommandFor("config logs enable");
        CommandResult result3 = configCmd.execute("config logs enable");
        assertFalse(result3.shouldExit());

        // Execute exit
        Command exitCmd = findCommandFor("exit");
        CommandResult result4 = exitCmd.execute("exit");
        assertTrue(result4.shouldExit());

        // Verify outputs
        assertTrue(outputAdapter.helpCalled);
        assertTrue(outputAdapter.exitCalled);
        assertTrue(outputAdapter.results.size() > 0);
        assertTrue(outputAdapter.successes.size() > 0);
    }

    @Test
    @DisplayName("Integration: Invalid time input should produce error")
    void shouldProduceErrorForInvalidTimeInput() {
        Command timeCmd = findCommandFor("invalid");
        CommandResult result = timeCmd.execute("invalid");

        assertFalse(result.shouldExit());
        assertTrue(outputAdapter.errors.size() > 0);
    }

    @Test
    @DisplayName("Integration: Config with invalid format should show usage")
    void shouldShowUsageForInvalidConfigFormat() {
        Command configCmd = findCommandFor("config invalid");
        CommandResult result = configCmd.execute("config invalid");

        assertFalse(result.shouldExit());
        assertTrue(outputAdapter.errors.size() > 0);
        assertTrue(outputAdapter.infos.size() > 0); // Usage info
    }

    // ===== Helper Methods =====

    private Command findCommandFor(String input) {
        return commands.stream()
            .filter(command -> command.canHandle(input))
            .findFirst()
            .orElse(null);
    }

    /**
     * Test adapter that captures all output for verification.
     */
    private static class TestOutputAdapter implements OutputAdapter {
        List<String> results = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        List<String> successes = new ArrayList<>();
        List<String> infos = new ArrayList<>();
        boolean welcomeCalled = false;
        boolean helpCalled = false;
        boolean exitCalled = false;

        @Override
        public void showWelcome() {
            welcomeCalled = true;
        }

        @Override
        public String readInput(Scanner scanner) {
            return "";
        }

        @Override
        public void showResult(String message) {
            results.add(message);
        }

        @Override
        public void showError(String message) {
            errors.add(message);
        }

        @Override
        public void showHelp() {
            helpCalled = true;
        }

        @Override
        public void showExit() {
            exitCalled = true;
        }

        @Override
        public void showBlankLine() {
            // No-op
        }

        @Override
        public void showSuccess(String message) {
            successes.add(message);
        }

        @Override
        public void showInfo(String message) {
            infos.add(message);
        }
    }
}
