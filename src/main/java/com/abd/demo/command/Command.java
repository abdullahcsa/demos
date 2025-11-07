package com.abd.demo.command;

/**
 * Command Strategy Interface
 * Represents a command that can be executed in the application.
 * Follows Command Pattern + Strategy Pattern.
 */
public interface Command {
    /**
     * Tests if this command can handle the given input.
     *
     * @param input user input string
     * @return true if this command should handle the input
     */
    boolean canHandle(String input);

    /**
     * Executes the command with the given input.
     *
     * @param input user input string
     * @return result of command execution
     */
    CommandResult execute(String input);

    /**
     * Returns the priority of this command.
     * Lower numbers = higher priority.
     * Allows explicit ordering instead of relying on map insertion order.
     *
     * @return priority value (default: 100)
     */
    default int getPriority() {
        return 100;
    }
}