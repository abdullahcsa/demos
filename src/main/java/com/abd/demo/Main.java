package com.abd.demo;

import com.abd.demo.domain.Time;
import com.abd.demo.service.TimeParser;
import java.util.Scanner;

public class Main {
    private static final String EXIT_COMMAND = "exit";
    private static final String HELP_COMMAND = "help";
    private final TimeParser timeParser;

    public Main() {
        this.timeParser = new TimeParser();
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
        System.out.println("  Time Input Application");
        System.out.println("================================");
        System.out.println("Enter time in format HH:MM or H:M");
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
            System.out.println(time.toString());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printHelp() {
        System.out.println("\nTime Input Application");
        System.out.println("---------------------");
        System.out.println("Enter time in format HH:MM or H:M");
        System.out.println("\nExamples:");
        System.out.println("  14:30  -> 14:30");
        System.out.println("  9:45   -> 09:45");
        System.out.println("  0:00   -> 00:00");
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

