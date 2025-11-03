package com.abd.demo;

import java.util.Scanner;

public class Main {
    private static final String EXIT_COMMAND = "exit";
    private static final String HELP_COMMAND = "help";

    public static void main(String[] args) {
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
        }

        scanner.close();
    }

    private static void printWelcomeMessage() {
        System.out.println("================================");
        System.out.println("  DemoBritishSpokenTime");
        System.out.println("================================");
        System.out.println("Type 'help' for available commands");
        System.out.println("Type 'exit' to quit");
        System.out.println();
    }

    private static void printExitMessage() {
        System.out.println("\nGoodbye!");
    }

    private static boolean shouldExit(String input) {
        return EXIT_COMMAND.equalsIgnoreCase(input) ||
               "quit".equalsIgnoreCase(input) ||
               "q".equalsIgnoreCase(input);
    }

    private static void processCommand(String input) {
        if (HELP_COMMAND.equalsIgnoreCase(input)) {
            printHelp();
        } else {
            System.out.println("Echo: " + input);
        }
    }

    private static void printHelp() {
        System.out.println("\nAvailable commands:");
        System.out.println("  help  - Show this help message");
        System.out.println("  exit  - Exit the application");
        System.out.println("  quit  - Exit the application");
        System.out.println("  q     - Exit the application");
        System.out.println("\nAny other input will be echoed back");
        System.out.println();
    }

    public static String getMessage() {
        return "Hello, World!";
    }
}
