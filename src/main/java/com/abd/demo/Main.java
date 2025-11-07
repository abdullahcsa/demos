package com.abd.demo;

import com.abd.demo.adapter.ConsoleAdapter;
import com.abd.demo.adapter.OutputAdapter;
import com.abd.demo.command.*;
import com.abd.demo.config.LoggerConfigUtil;
import com.abd.demo.service.TimeConverterService;
import com.abd.demo.service.TimeParser;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main application entry point.
 * Orchestrates the CLI application using DDD Hexagonal Architecture + Strategy Pattern.
 * Layers:
 * - Adapter Layer: OutputAdapter (port), ConsoleAdapter (adapter implementation)
 * - Application Layer: Command implementations (application services)
 * - Domain Layer: Time (core business logic)
 * Follows Open/Closed Principle - new commands can be added without modifying this class.
 */
@Slf4j
public class Main {
    private final OutputAdapter output;
    private final List<Command> commands;

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
        // Initialize console logging (disabled by default)
        LoggerConfigUtil.initializeConsoleLogging();

        log.info("Initializing British Spoken Time application");
        this.output = output;
        this.commands = createCommands();
        log.debug("Loaded {} commands", commands.size());
    }

    public static void main(String[] args) {
        log.info("Starting British Spoken Time Converter application");
        new Main().run();
        log.info("Application terminated");
    }

    private List<Command> createCommands() {
        TimeParser timeParser = new TimeParser();
        TimeConverterService timeConverterService = new TimeConverterService();

        return Stream.of(
            new ExitCommand(output),
            new HelpCommand(output),
            new ConfigCommand(output),
            new TimeConversionCommand(timeParser, timeConverterService, output)
        )
         .sorted(Comparator.comparingInt(Command::getPriority))
         .collect(Collectors.toList());
    }

    public void run() {
        log.info("Starting main application loop");
        try (Scanner scanner = new Scanner(System.in)) {
            output.showWelcome();

            while (true) {
                String input = output.readInput(scanner);
                if (input.isEmpty()) {
                    log.trace("Empty input received, skipping");
                    continue;
                }

                log.debug("Processing user input: '{}'", input);
                CommandResult result = processCommand(input);

                if (result.shouldShowBlankLine()) {
                    output.showBlankLine();
                }

                if (result.shouldExit()) {
                    log.info("Exit command received, terminating application");
                    break;
                }
            }
        }
        log.debug("Main application loop completed");
    }

    private CommandResult processCommand(String input) {
        return commands.stream()
            .filter(command -> {
                boolean canHandle = command.canHandle(input);
                if (canHandle) {
                    log.debug("Command {} will handle input '{}'", command.getClass().getSimpleName(), input);
                }
                return canHandle;
            })
            .findFirst()
            .map(command -> command.execute(input))
            .orElse(CommandResult.continueRunning()); // Should never happen with default handler
    }

    public static String getMessage() {
        return "Hello, World!";
    }
}

