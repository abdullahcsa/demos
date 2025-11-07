package com.abd.demo.command;

import com.abd.demo.adapter.OutputAdapter;
import com.abd.demo.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfigCommandTest {

    @Mock
    private OutputAdapter mockOutput;

    private ConfigCommand configCommand;
    private AppConfig appConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        configCommand = new ConfigCommand(mockOutput);
        appConfig = AppConfig.getInstance();
        // Reset to default state
        appConfig.setConsoleLogsEnabled(false);
    }

    @Test
    void canHandle_shouldReturnTrue_forConfigCommand() {
        assertTrue(configCommand.canHandle("config"));
        assertTrue(configCommand.canHandle("CONFIG"));
        assertTrue(configCommand.canHandle("config logs enable"));
    }

    @Test
    void canHandle_shouldReturnFalse_forNonConfigCommand() {
        assertFalse(configCommand.canHandle("help"));
        assertFalse(configCommand.canHandle("exit"));
        assertFalse(configCommand.canHandle("12:30"));
    }

    @Test
    void execute_shouldShowCurrentConfig_whenJustConfig() {
        CommandResult result = configCommand.execute("config");

        verify(mockOutput).showInfo("Current Configuration:");
        verify(mockOutput).showInfo("  Console Logs: disabled");
        assertFalse(result.shouldExit());
        assertTrue(result.shouldShowBlankLine());
    }

    @Test
    void execute_shouldEnableLogging_whenConfigLogsEnable() {
        CommandResult result = configCommand.execute("config logs enable");

        assertTrue(appConfig.isConsoleLogsEnabled());
        verify(mockOutput).showSuccess("Console logging enabled");
        assertFalse(result.shouldExit());
        assertTrue(result.shouldShowBlankLine());
    }

    @Test
    void execute_shouldDisableLogging_whenConfigLogsDisable() {
        // First enable
        appConfig.setConsoleLogsEnabled(true);

        CommandResult result = configCommand.execute("config logs disable");

        assertFalse(appConfig.isConsoleLogsEnabled());
        verify(mockOutput).showSuccess("Console logging disabled");
        assertFalse(result.shouldExit());
        assertTrue(result.shouldShowBlankLine());
    }

    @Test
    void execute_shouldHandleCaseInsensitive_enable() {
        configCommand.execute("CONFIG LOGS ENABLE");
        assertTrue(appConfig.isConsoleLogsEnabled());

        configCommand.execute("CoNfIg LoGs EnAbLe");
        assertTrue(appConfig.isConsoleLogsEnabled());
    }

    @Test
    void execute_shouldHandleCaseInsensitive_disable() {
        configCommand.execute("CONFIG LOGS DISABLE");
        assertFalse(appConfig.isConsoleLogsEnabled());

        configCommand.execute("CoNfIg LoGs DiSaBlE");
        assertFalse(appConfig.isConsoleLogsEnabled());
    }

    @Test
    void execute_shouldShowError_forInvalidAction() {
        CommandResult result = configCommand.execute("config logs invalid");

        verify(mockOutput).showError("Invalid action for logs. Use 'enable' or 'disable'");
        assertFalse(result.shouldExit());
    }

    @Test
    void execute_shouldShowUsage_forInvalidFormat() {
        CommandResult result = configCommand.execute("config invalid");

        verify(mockOutput).showError("Invalid config command format");
        verify(mockOutput).showInfo("Usage:");
        verify(mockOutput).showInfo("  config               - Show current configuration");
        verify(mockOutput).showInfo("  config logs enable   - Enable console logging");
        verify(mockOutput).showInfo("  config logs disable  - Disable console logging");
        assertFalse(result.shouldExit());
    }

    @Test
    void execute_shouldShowUsage_forTooManyArguments() {
        CommandResult result = configCommand.execute("config logs enable extra");

        verify(mockOutput).showError("Invalid config command format");
        assertFalse(result.shouldExit());
    }

    @Test
    void execute_shouldShowUsage_forWrongSubcommand() {
        CommandResult result = configCommand.execute("config wrongsub enable");

        verify(mockOutput).showError("Invalid config command format");
        assertFalse(result.shouldExit());
    }

    @Test
    void execute_shouldToggleMultipleTimes() {
        // Enable
        configCommand.execute("config logs enable");
        assertTrue(appConfig.isConsoleLogsEnabled());

        // Disable
        configCommand.execute("config logs disable");
        assertFalse(appConfig.isConsoleLogsEnabled());

        // Enable again
        configCommand.execute("config logs enable");
        assertTrue(appConfig.isConsoleLogsEnabled());
    }

    @Test
    void getPriority_shouldReturn8() {
        assertEquals(8, configCommand.getPriority());
    }

    @Test
    void execute_shouldReturnContinueRunning_always() {
        CommandResult result1 = configCommand.execute("config");
        assertFalse(result1.shouldExit());

        CommandResult result2 = configCommand.execute("config logs enable");
        assertFalse(result2.shouldExit());

        CommandResult result3 = configCommand.execute("config logs disable");
        assertFalse(result3.shouldExit());

        CommandResult result4 = configCommand.execute("config invalid");
        assertFalse(result4.shouldExit());
    }
}
