package com.abd.demo.command;

/**
 * Immutable value object representing the result of command execution.
 * Follows DDD Value Object pattern.
 */
public class CommandResult {
    private final boolean shouldExit;
    private final boolean shouldShowBlankLine;

    private CommandResult(boolean shouldExit, boolean shouldShowBlankLine) {
        this.shouldExit = shouldExit;
        this.shouldShowBlankLine = shouldShowBlankLine;
    }

    public static CommandResult continueRunning() {
        return new CommandResult(false, true);
    }

    public static CommandResult exit() {
        return new CommandResult(true, false);
    }

    public static CommandResult continueWithoutBlankLine() {
        return new CommandResult(false, false);
    }

    public boolean shouldExit() {
        return shouldExit;
    }

    public boolean shouldShowBlankLine() {
        return shouldShowBlankLine;
    }
}