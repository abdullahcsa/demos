package com.abd.demo.adapter;

import java.util.Scanner;

/**
 * Output adapter interface - Outbound Port in Hexagonal Architecture.
 * Defines the contract for all output adapter implementations.
 *
 * Allows multiple adapters to be plugged in:
 * - ConsoleAdapter (terminal/CLI)
 * - WebAdapter (REST API responses)
 * - GUIAdapter (Swing/JavaFX)
 * - FileAdapter (write to file)
 * - TestAdapter (for testing)
 *
 * Part of the Adapter layer in DDD.
 * Follows Interface Segregation Principle and Dependency Inversion Principle.
 */
public interface OutputAdapter {

    /**
     * Display welcome message to user.
     */
    void showWelcome();

    /**
     * Display help information.
     */
    void showHelp();

    /**
     * Display exit/goodbye message.
     */
    void showExit();

    /**
     * Display conversion result.
     * @param result the time conversion result
     */
    void showResult(String result);

    /**
     * Display error message.
     * @param error the error message
     */
    void showError(String error);

    /**
     * Display success message.
     * @param message the success message
     */
    void showSuccess(String message);

    /**
     * Display informational message.
     * @param message the info message
     */
    void showInfo(String message);

    /**
     * Display blank line or separator.
     */
    void showBlankLine();

    /**
     * Read input from user.
     * @param scanner input source
     * @return user input as string
     */
    String readInput(Scanner scanner);
}
