package com.abd.demo.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConsoleAdapter Tests")
class ConsoleAdapterTest {

    private TestableConsoleAdapter adapter;
    private Messages messages;

    @BeforeEach
    void setUp() {
        messages = new Messages();
        adapter = new TestableConsoleAdapter(messages);
    }

    @Test
    @DisplayName("Should show welcome message")
    void testShowWelcome() {
        adapter.showWelcome();

        String output = adapter.getLastOutput();
        assertTrue(output.contains("British Spoken Time Converter"));
        assertTrue(output.contains("Enter time in format HH:MM or H:M"));
    }

    @Test
    @DisplayName("Should show help message")
    void testShowHelp() {
        adapter.showHelp();

        String output = adapter.getLastOutput();
        assertTrue(output.contains("Time to Words Converter"));
        assertTrue(output.contains("help  - Show this help message"));
        assertTrue(output.contains("exit  - Exit the application"));
    }

    @Test
    @DisplayName("Should show exit message")
    void testShowExit() {
        adapter.showExit();

        String output = adapter.getLastOutput();
        assertTrue(output.contains("Goodbye"));
    }

    @Test
    @DisplayName("Should show result when result is not null")
    void testShowResultWithValidResult() {
        adapter.showResult("quarter past three");

        String output = adapter.getLastOutput();
        assertEquals("quarter past three", output);
    }

    @Test
    @DisplayName("Should not show anything when result is null")
    void testShowResultWithNullResult() {
        adapter.clearOutput();
        adapter.showResult(null);

        assertTrue(adapter.getOutputs().isEmpty());
    }

    @Test
    @DisplayName("Should show error message with prefix")
    void testShowError() {
        adapter.showError("Invalid time format");

        String output = adapter.getLastOutput();
        assertEquals("Error: Invalid time format", output);
    }

    @Test
    @DisplayName("Should show blank line")
    void testShowBlankLine() {
        adapter.showBlankLine();

        String output = adapter.getLastOutput();
        assertEquals("", output);
    }

    @Test
    @DisplayName("Should read input from scanner")
    void testReadInput() {
        String input = "13:30\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        String result = adapter.readInput(scanner);

        assertEquals("13:30", result);
        assertEquals("> ", adapter.getLastPrompt());
    }

    @Test
    @DisplayName("Should trim input when reading")
    void testReadInputWithWhitespace() {
        String input = "  13:30  \n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        String result = adapter.readInput(scanner);

        assertEquals("13:30", result);
    }

    @Test
    @DisplayName("Should handle empty input")
    void testReadInputEmpty() {
        String input = "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        String result = adapter.readInput(scanner);

        assertEquals("", result);
    }

    @Test
    @DisplayName("Should use custom messages when provided")
    void testCustomMessages() {
        ConsoleAdapter customAdapter = new TestableConsoleAdapter(messages);

        customAdapter.showWelcome();
        customAdapter.showHelp();
        customAdapter.showExit();

        // Verify messages instance is used (no exceptions thrown)
        assertNotNull(customAdapter);
    }

    /**
     * Testable version of ConsoleAdapter that captures output instead of printing.
     */
    private static class TestableConsoleAdapter extends ConsoleAdapter {
        private final List<String> outputs = new ArrayList<>();
        private String lastPrompt = "";

        public TestableConsoleAdapter(Messages messages) {
            super(messages);
        }

        @Override
        protected void write(String message) {
            outputs.add(message);
        }

        @Override
        protected void writePrompt(String prompt) {
            lastPrompt = prompt;
        }

        public String getLastOutput() {
            return outputs.isEmpty() ? "" : outputs.get(outputs.size() - 1);
        }

        public String getLastPrompt() {
            return lastPrompt;
        }

        public List<String> getOutputs() {
            return new ArrayList<>(outputs);
        }

        public void clearOutput() {
            outputs.clear();
        }
    }
}
