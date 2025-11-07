package com.abd.demo.adapter;

/**
 * Message provider for adapter/presentation text.
 * Centralized message storage following Single Responsibility Principle.
 * Can be easily extended to support internationalization (i18n).
 *
 * Part of the Adapter layer in DDD - infrastructure concern.
 */
public class Messages {

    // Prompts
    public static final String PROMPT = "> ";

    // Application Messages
    private static final String WELCOME = String.join("\n",
        "================================",
        "  British Spoken Time Converter",
        "================================",
        "Enter time in format HH:MM or H:M",
        "Time will be converted to words",
        "",
        "Type 'help' for available commands",
        "Type 'config logs enable' to enable console logging",
        "Type 'q | exit' to quit",
        ""
    );

    private static final String HELP = String.join("\n",
        "",
        "Time to Words Converter",
        "-----------------------",
        "Enter time in format HH:MM or H:M",
        "Time will be converted to words",
        "",
        "Examples:",
        "  6:32   -> six thirty two",
        "",
        "Commands:",
        "  help                 - Show this help message",
        "  config               - Show current configuration",
        "  config logs enable   - Enable console logging (shows debug info)",
        "  config logs disable  - Disable console logging (default, clean output)",
        "  exit                 - Exit the application",
        "  quit                 - Exit the application",
        "  q                    - Exit the application",
        "",
        "Note: Console logs are disabled by default for clean user experience.",
        "      Enable logs to see detailed conversion process information.",
        ""
    );

    private static final String EXIT = "\nGoodbye!";
    private static final String ERROR_PREFIX = "Error: ";
    private static final String SUCCESS_PREFIX = "Success: ";
    private static final String INFO_PREFIX = "";
    private static final String BLANK = "";

    // Getters for messages
    public String getWelcome() {
        return WELCOME;
    }

    public String getHelp() {
        return HELP;
    }

    public String getExit() {
        return EXIT;
    }

    public String formatError(String error) {
        return ERROR_PREFIX + error;
    }

    public String formatSuccess(String message) {
        return SUCCESS_PREFIX + message;
    }

    public String formatInfo(String message) {
        return INFO_PREFIX + message;
    }

    public String getBlank() {
        return BLANK;
    }

    public String getPrompt() {
        return PROMPT;
    }
}
