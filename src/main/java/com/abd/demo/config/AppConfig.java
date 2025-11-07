package com.abd.demo.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Application configuration singleton.
 * Manages runtime configuration settings for the application.
 */
@Slf4j
public class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    @Getter
    private boolean consoleLogsEnabled;

    private AppConfig() {
        // Default: console logs disabled for clean user experience
        this.consoleLogsEnabled = false;
        log.debug("AppConfig initialized with consoleLogsEnabled={}", consoleLogsEnabled);
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    /**
     * Enable or disable console logging.
     *
     * @param enabled true to enable console logs, false to disable
     */
    public void setConsoleLogsEnabled(boolean enabled) {
        log.info("Changing consoleLogsEnabled from {} to {}", this.consoleLogsEnabled, enabled);
        this.consoleLogsEnabled = enabled;
        LoggerConfigUtil.setConsoleLoggingEnabled(enabled);
    }
}
