package com.abd.demo;

import com.abd.demo.domain.Time;
import com.abd.demo.service.TimeParser;
import com.abd.demo.service.TimeConverterService;
import com.abd.demo.adapter.ConsoleAdapter;
import com.abd.demo.adapter.OutputAdapter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Main application entry point.
 * Orchestrates the CLI application using DDD Hexagonal Architecture:
 *
 * Layers:
 * - Adapter Layer: OutputAdapter (port), ConsoleAdapter (adapter implementation)
 * - Application Layer: TimeParser, TimeConverterService (application logic)
 * - Domain Layer: Time (core business logic)
 *
 * Follows Dependency Inversion Principle - depends on OutputAdapter interface,
 * not concrete implementation. This allows plugging in different adapters.
 */
public class Main {
    private final TimeParser timeParser;
    private final TimeConverterService timeConverterService;
    private final OutputAdapter output;
    private final Map<Predicate<String>, Consumer<String>> commandHandlers;
    private final Set<String> exitCommands;

    public Main() {
        this(new ConsoleAdapter());
    }

    /**
     * Constructor with dependency injection.
     * Accepts any OutputAdapter implementation (Console, Web, GUI, File, etc.)
     *
     * @param output the output adapter to use for UI operations
     */
    public Main(OutputAdapter output) {
        this.timeParser = new TimeParser();
        this.timeConverterService = new TimeConverterService();
        this.output = output;
        this.exitCommands = Set.of("exit", "quit", "q");
        this.commandHandlers = createCommandHandlers();
    }

    public static void main(String[] args) {
        new Main().run();
    }

    private Map<Predicate<String>, Consumer<String>> createCommandHandlers() {
        return Map.of(
            input -> exitCommands.contains(input.toLowerCase()), input -> {},
            input -> "help".equalsIgnoreCase(input), input -> output.showHelp(),
            input -> true, this::convertAndDisplay // default handler
        );
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            output.showWelcome();

            while (true) {
                String input = output.readInput(scanner);

                if (input.isEmpty()) continue;
                if (exitCommands.contains(input.toLowerCase())) {
                    output.showExit();
                    break;
                }

                processCommand(input);
                output.showBlankLine();
            }
        }
    }

    private void processCommand(String input) {
        commandHandlers.entrySet().stream()
            .filter(entry -> entry.getKey().test(input))
            .findFirst()
            .ifPresent(entry -> entry.getValue().accept(input));
    }

    private void convertAndDisplay(String input) {
        output.showResult(
            parseTime(input)
                .map(timeConverterService::convert)
                .orElse(null)
        );
    }

    private Optional<Time> parseTime(String input) {
        try {
            return Optional.of(timeParser.parse(input));
        } catch (IllegalArgumentException e) {
            output.showError(e.getMessage());
            return Optional.empty();
        }
    }

    public static String getMessage() {
        return "Hello, World!";
    }
}

