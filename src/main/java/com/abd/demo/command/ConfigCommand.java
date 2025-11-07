package com.abd.demo.command;

import com.abd.demo.adapter.OutputAdapter;
import com.abd.demo.config.AppConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * Command to handle configuration changes.
 * Single Responsibility: Handle configuration-related commands.
 *
 * Usage:
 *   config logs enable   - Enable console logging
 *   config logs disable  - Disable console logging
 *   config               - Show current configuration
 */
@Slf4j
public class ConfigCommand implements Command {
    private static final String CONFIG_PREFIX = "config";
    private static final String LOGS_KEY = "logs";
    private static final String ENABLE_ACTION = "enable";
    private static final String DISABLE_ACTION = "disable";

    private final OutputAdapter output;
    private final AppConfig config;

    public ConfigCommand(OutputAdapter output) {
        this.output = output;
        this.config = AppConfig.getInstance();
        log.debug("ConfigCommand initialized");
    }

    @Override
    public boolean canHandle(String input) {
        return input.toLowerCase().startsWith(CONFIG_PREFIX);
    }

    @Override
    public CommandResult execute(String input) {
        log.info("Executing config command: '{}'", input);

        String[] parts = input.trim().split("\\s+");

        if (parts.length == 1) {
            // Just "config" - show current settings
            showCurrentConfig();
            return CommandResult.continueRunning();
        }

        if (parts.length == 3 && LOGS_KEY.equalsIgnoreCase(parts[1])) {
            String action = parts[2];
            if (ENABLE_ACTION.equalsIgnoreCase(action)) {
                config.setConsoleLogsEnabled(true);
                output.showSuccess("Console logging enabled");
                log.info("Console logging enabled");
            } else if (DISABLE_ACTION.equalsIgnoreCase(action)) {
                config.setConsoleLogsEnabled(false);
                output.showSuccess("Console logging disabled");
                log.info("Console logging disabled");
            } else {
                output.showError("Invalid action for logs. Use 'enable' or 'disable'");
                log.warn("Invalid logs action: {}", action);
            }
            return CommandResult.continueRunning();
        }

        // Invalid command format
        showUsage();
        return CommandResult.continueRunning();
    }

    @Override
    public int getPriority() {
        return 8; // Higher priority than time conversion
    }

    private void showCurrentConfig() {
        output.showInfo("Current Configuration:");
        output.showInfo("  Console Logs: " + (config.isConsoleLogsEnabled() ? "enabled" : "disabled"));
    }

    private void showUsage() {
        output.showError("Invalid config command format");
        output.showInfo("Usage:");
        output.showInfo("  config               - Show current configuration");
        output.showInfo("  config logs enable   - Enable console logging");
        output.showInfo("  config logs disable  - Disable console logging");
    }
}
