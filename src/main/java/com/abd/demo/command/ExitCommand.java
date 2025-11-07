package com.abd.demo.command;

import com.abd.demo.adapter.OutputAdapter;

import java.util.Set;

/**
 * Command to handle application exit.
 * Single Responsibility: Handle exit requests only.
 */
public class ExitCommand implements Command {
    private static final Set<String> EXIT_KEYWORDS = Set.of("exit", "quit", "q");

    private final OutputAdapter output;

    public ExitCommand(OutputAdapter output) {
        this.output = output;
    }

    @Override
    public boolean canHandle(String input) {
        return EXIT_KEYWORDS.contains(input.toLowerCase());
    }

    @Override
    public CommandResult execute(String input) {
        output.showExit();
        return CommandResult.exit();
    }

    @Override
    public int getPriority() {
        return 5; // Highest priority
    }
}