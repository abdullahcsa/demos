package com.abd.demo.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.slf4j.LoggerFactory;

import static ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME;

/**
 * Utility class to dynamically configure logging behavior.
 * Allows enabling/disabling console logging at runtime by detaching/attaching the console appender.
 */
public class LoggerConfigUtil {
    private static final String CONSOLE_APPENDER_NAME = "CONSOLE";
    private static Appender<ILoggingEvent> consoleAppenderCache = null;

    /**
     * Enable or disable console logging dynamically.
     * Uses detach/attach approach for clean runtime toggling.
     *
     * @param enabled true to enable console logging, false to disable
     */
    public static void setConsoleLoggingEnabled(boolean enabled) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger(ROOT_LOGGER_NAME);

        if (enabled) {
            // Enable: Attach console appender if not already attached
            if (rootLogger.getAppender(CONSOLE_APPENDER_NAME) == null && consoleAppenderCache != null) {
                rootLogger.addAppender(consoleAppenderCache);
            }
        } else {
            // Disable: Detach console appender and cache it for later re-attachment
            Appender<ILoggingEvent> consoleAppender = rootLogger.getAppender(CONSOLE_APPENDER_NAME);
            if (consoleAppender != null) {
                consoleAppenderCache = consoleAppender;
                rootLogger.detachAppender(CONSOLE_APPENDER_NAME);
            }
        }
    }

    /**
     * Initialize console logging based on configuration.
     * Call this at application startup.
     */
    public static void initializeConsoleLogging() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger(ROOT_LOGGER_NAME);

        // Cache the console appender reference
        consoleAppenderCache = rootLogger.getAppender(CONSOLE_APPENDER_NAME);

        // Apply initial configuration
        boolean enabled = AppConfig.getInstance().isConsoleLogsEnabled();
        setConsoleLoggingEnabled(enabled);
    }
}
