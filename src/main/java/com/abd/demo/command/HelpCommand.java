package com.abd.demo.command;

import com.abd.demo.adapter.OutputAdapter;

/**
 * Command to display help information.
 * Single Responsibility: Handle help requests only.
 */
public class HelpCommand implements Command {
    private final OutputAdapter output;

    public HelpCommand(OutputAdapter output) {
        this.output = output;
    }

    @Override
    public boolean canHandle(String input) {
        return "help".equalsIgnoreCase(input);
    }

    @Override
    public CommandResult execute(String input) {
        output.showHelp();
        return CommandResult.continueRunning();
    }

    @Override
    public int getPriority() {
        return 10; // High priority
    }
}