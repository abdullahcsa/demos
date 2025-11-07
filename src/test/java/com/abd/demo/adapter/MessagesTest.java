package com.abd.demo.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Messages Tests")
class MessagesTest {

    private Messages messages;

    @BeforeEach
    void setUp() {
        messages = new Messages();
    }

    @Test
    @DisplayName("Should return welcome message")
    void testGetWelcome() {
        String welcome = messages.getWelcome();

        assertNotNull(welcome);
        assertTrue(welcome.contains("British Spoken Time Converter"));
        assertTrue(welcome.contains("Enter time in format HH:MM or H:M"));
        assertTrue(welcome.contains("Time will be converted to words"));
        assertTrue(welcome.contains("Type 'help' for available commands"));
        assertTrue(welcome.contains("Type 'q | exit' to quit"));
    }

    @Test
    @DisplayName("Should return help message")
    void testGetHelp() {
        String help = messages.getHelp();

        assertNotNull(help);
        assertTrue(help.contains("Time to Words Converter"));
        assertTrue(help.contains("Enter time in format HH:MM or H:M"));
        assertTrue(help.contains("Examples:"));
        assertTrue(help.contains("6:32   -> six thirty two"));
        assertTrue(help.contains("Commands:"));
        assertTrue(help.contains("help") && help.contains("Show this help message"));
        assertTrue(help.contains("config") && help.contains("Show current configuration"));
        assertTrue(help.contains("exit") && help.contains("Exit the application"));
        assertTrue(help.contains("quit") && help.contains("Exit the application"));
        assertTrue(help.contains("q") && help.contains("Exit the application"));
    }

    @Test
    @DisplayName("Should return exit message")
    void testGetExit() {
        String exit = messages.getExit();

        assertNotNull(exit);
        assertTrue(exit.contains("Goodbye"));
    }

    @Test
    @DisplayName("Should format error message with prefix")
    void testFormatError() {
        String error = messages.formatError("Invalid input");

        assertEquals("Error: Invalid input", error);
    }

    @Test
    @DisplayName("Should format error with empty string")
    void testFormatErrorEmpty() {
        String error = messages.formatError("");

        assertEquals("Error: ", error);
    }

    @Test
    @DisplayName("Should return blank message")
    void testGetBlank() {
        String blank = messages.getBlank();

        assertNotNull(blank);
        assertEquals("", blank);
    }

    @Test
    @DisplayName("Should return prompt")
    void testGetPrompt() {
        String prompt = messages.getPrompt();

        assertNotNull(prompt);
        assertEquals("> ", prompt);
    }

    @Test
    @DisplayName("Should return consistent messages across multiple calls")
    void testMessageConsistency() {
        String welcome1 = messages.getWelcome();
        String welcome2 = messages.getWelcome();

        assertEquals(welcome1, welcome2);

        String help1 = messages.getHelp();
        String help2 = messages.getHelp();

        assertEquals(help1, help2);
    }

    @Test
    @DisplayName("Should have non-empty welcome message")
    void testWelcomeNotEmpty() {
        assertFalse(messages.getWelcome().isEmpty());
    }

    @Test
    @DisplayName("Should have non-empty help message")
    void testHelpNotEmpty() {
        assertFalse(messages.getHelp().isEmpty());
    }

    @Test
    @DisplayName("Should have non-empty exit message")
    void testExitNotEmpty() {
        assertFalse(messages.getExit().isEmpty());
    }

    @Test
    @DisplayName("Should have accessible PROMPT constant")
    void testPromptConstant() {
        assertEquals("> ", Messages.PROMPT);
    }
}
