package com.abd.demo;

import com.abd.demo.domain.Time;
import com.abd.demo.service.TimeParser;
import com.abd.demo.service.TimeConverterService;
import java.util.Scanner;

public class Main {
    private static final String EXIT_COMMAND = "exit";
    private static final String HELP_COMMAND = "help";
    private final TimeParser timeParser;
    private final TimeConverterService timeConverterService;

    public Main() {
        this.timeParser = new TimeParser();
        this.timeConverterService = new TimeConverterService();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        printWelcomeMessage();

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            if (shouldExit(input)) {
                printExitMessage();
                break;
            }

            processCommand(input);
            System.out.println();
        }

        scanner.close();
    }

    private void printWelcomeMessage() {
        System.out.println("================================");
        System.out.println("  British Spoken Time Converter");
        System.out.println("================================");
        System.out.println("Enter time in format HH:MM or H:M");
        System.out.println("Time will be converted to words");
        System.out.println("Type 'help' for available commands");
        System.out.println("Type 'q | exit' to quit");
        System.out.println();
    }

    private void printExitMessage() {
        System.out.println("\nGoodbye!");
    }

    private boolean shouldExit(String input) {
        return EXIT_COMMAND.equalsIgnoreCase(input) ||
               "quit".equalsIgnoreCase(input) ||
               "q".equalsIgnoreCase(input);
    }

    private void processCommand(String input) {
        if (HELP_COMMAND.equalsIgnoreCase(input)) {
            printHelp();
        } else {
            parseAndDisplayTime(input);
        }
    }

    private void parseAndDisplayTime(String input) {
        try {
            Time time = timeParser.parse(input);
            String timeInWords = timeConverterService.convert(time);
            System.out.println(timeInWords);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printHelp() {
        System.out.println("\nTime to Words Converter");
        System.out.println("-----------------------");
        System.out.println("Enter time in format HH:MM or H:M");
        System.out.println("Time will be converted to words");
        System.out.println("\nExamples:");
        System.out.println("  6:32   -> six thirty two");
        System.out.println("\nCommands:");
        System.out.println("  help  - Show this help message");
        System.out.println("  exit  - Exit the application");
        System.out.println("  quit  - Exit the application");
        System.out.println("  q     - Exit the application");
        System.out.println();
    }

    public static String getMessage() {
        return "Hello, World!";
    }
}

