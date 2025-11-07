package com.abd.demo.adapter;

import java.util.Optional;
import java.util.Scanner;

/**
 * Console/Terminal adapter implementation.
 * Implements OutputAdapter interface for CLI/terminal interactions.
 *
 * Follows Single Responsibility Principle - only responsible for console I/O.
 * Adapter pattern for console/terminal in Hexagonal Architecture.
 *
 * Single point of interaction with System.out/System.in.
 * Makes testing easier by allowing output to be intercepted.
 */
public class ConsoleAdapter implements OutputAdapter {
    private final Messages messages;

    public ConsoleAdapter() {
        this(new Messages());
    }

    public ConsoleAdapter(Messages messages) {
        this.messages = messages;
    }

    // Single method for all output operations - SRP compliance
    protected void write(String message) {
        System.out.println(message);
    }

    protected void writePrompt(String prompt) {
        System.out.print(prompt);
    }

    // OutputAdapter interface implementation
    @Override
    public void showWelcome() {
        write(messages.getWelcome());
    }

    @Override
    public void showHelp() {
        write(messages.getHelp());
    }

    @Override
    public void showExit() {
        write(messages.getExit());
    }

    @Override
    public void showResult(String result) {
        Optional.ofNullable(result).ifPresent(this::write);
    }

    @Override
    public void showError(String error) {
        write(messages.formatError(error));
    }

    @Override
    public void showSuccess(String message) {
        write(messages.formatSuccess(message));
    }

    @Override
    public void showInfo(String message) {
        write(messages.formatInfo(message));
    }

    @Override
    public void showBlankLine() {
        write(messages.getBlank());
    }

    @Override
    public String readInput(Scanner scanner) {
        writePrompt(messages.getPrompt());
        return scanner.nextLine().trim();
    }
}
