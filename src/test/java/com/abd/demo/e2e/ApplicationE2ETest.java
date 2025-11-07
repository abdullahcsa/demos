package com.abd.demo.e2e;

import com.abd.demo.Main;
import com.abd.demo.adapter.OutputAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * End-to-End tests for the complete application.
 * Tests full user workflows from input to output using the real Main class.
 * No mocking - tests the entire application stack.
 */
@DisplayName("Application End-to-End Tests")
class ApplicationE2ETest {

    @Test
    @DisplayName("E2E: Complete user session with help, time conversions, and exit")
    void shouldHandleCompleteUserSession() {
        TestOutputAdapter adapter = new TestOutputAdapter();
        adapter.setInputs("help", "12:00", "14:30", "exit");

        Main app = new Main(adapter);
        app.run();

        // Verify welcome was shown
        assertTrue(adapter.welcomeCalled, "Welcome should be displayed");

        // Verify help was shown
        assertTrue(adapter.helpCalled, "Help should be displayed");

        // Verify time conversions worked
        assertTrue(adapter.results.contains("noon"), "Should convert 12:00 to noon");
        assertTrue(adapter.results.contains("half past two"), "Should convert 14:30 to half past two");

        // Verify exit was shown
        assertTrue(adapter.exitCalled, "Exit message should be displayed");

        // Verify blank lines were added
        assertTrue(adapter.blankLineCalled, "Blank lines should be called");
    }

    @Test
    @DisplayName("E2E: User session with invalid input handling")
    void shouldHandleInvalidInputGracefully() {
        TestOutputAdapter adapter = new TestOutputAdapter();
        adapter.setInputs("invalid", "abc", "99:99", "exit");

        Main app = new Main(adapter);
        app.run();

        // Should have error messages
        assertTrue(adapter.errors.size() >= 3, "Should have multiple errors");

        // Should still exit gracefully
        assertTrue(adapter.exitCalled, "Should exit cleanly after errors");
    }

    @Test
    @DisplayName("E2E: Multiple time conversions in one session")
    void shouldHandleMultipleTimeConversions() {
        TestOutputAdapter adapter = new TestOutputAdapter();
        adapter.setInputs(
            "00:00",  // midnight
            "12:00",  // noon
            "3:15",   // quarter past three
            "8:45",   // quarter to nine
            "14:30",  // half past two
            "exit"
        );

        Main app = new Main(adapter);
        app.run();

        assertEquals(5, adapter.results.size(), "Should have 5 conversions");
        assertTrue(adapter.results.contains("midnight"));
        assertTrue(adapter.results.contains("noon"));
        assertTrue(adapter.results.contains("quarter past three"));
        assertTrue(adapter.results.contains("quarter to nine"));
        assertTrue(adapter.results.contains("half past two"));
    }

    @Test
    @DisplayName("E2E: Configuration changes during session")
    void shouldHandleConfigurationChanges() {
        TestOutputAdapter adapter = new TestOutputAdapter();
        adapter.setInputs(
            "config",                 // Show current config
            "config logs enable",     // Enable logs
            "12:00",                  // Convert time
            "config logs disable",    // Disable logs
            "14:30",                  // Convert time again
            "exit"
        );

        Main app = new Main(adapter);
        app.run();

        // Verify config commands executed
        assertTrue(adapter.infos.size() > 0, "Should show config info");
        assertTrue(adapter.successes.size() >= 2, "Should have success messages for enable/disable");

        // Verify time conversions still work after config changes
        assertTrue(adapter.results.contains("noon"));
        assertTrue(adapter.results.contains("half past two"));
    }

    @Test
    @DisplayName("E2E: Empty input should be ignored")
    void shouldIgnoreEmptyInput() {
        TestOutputAdapter adapter = new TestOutputAdapter();
        adapter.setInputs("", "", "12:00", "", "exit");

        Main app = new Main(adapter);
        app.run();

        // Should only process non-empty inputs
        assertEquals(1, adapter.results.size(), "Should only convert one time");
        assertTrue(adapter.results.contains("noon"));
    }

    @Test
    @DisplayName("E2E: Case-insensitive command handling")
    void shouldHandleCommandsCaseInsensitive() {
        TestOutputAdapter adapter = new TestOutputAdapter();
        adapter.setInputs(
            "HELP",
            "CONFIG LOGS ENABLE",
            "EXIT"
        );

        Main app = new Main(adapter);
        app.run();

        assertTrue(adapter.helpCalled, "HELP should work");
        assertTrue(adapter.successes.size() > 0, "CONFIG should work");
        assertTrue(adapter.exitCalled, "EXIT should work");
    }

    @Test
    @DisplayName("E2E: Hour-only input format")
    void shouldHandleHourOnlyInput() {
        TestOutputAdapter adapter = new TestOutputAdapter();
        adapter.setInputs("12", "0", "5", "exit");

        Main app = new Main(adapter);
        app.run();

        assertEquals(3, adapter.results.size());
        assertTrue(adapter.results.contains("noon"), "12 should convert to noon");
        assertTrue(adapter.results.contains("midnight"), "0 should convert to midnight");
        assertTrue(adapter.results.contains("five o'clock"), "5 should convert to five o'clock");
    }

    @Test
    @DisplayName("E2E: Mixed valid and invalid inputs")
    void shouldHandleMixedInputs() {
        TestOutputAdapter adapter = new TestOutputAdapter();
        adapter.setInputs(
            "12:00",      // valid
            "invalid",    // invalid
            "14:30",      // valid
            "99:99",      // invalid
            "8:45",       // valid
            "exit"
        );

        Main app = new Main(adapter);
        app.run();

        // Should have 3 successful conversions
        assertEquals(3, adapter.results.size());

        // Should have 2 errors
        assertTrue(adapter.errors.size() >= 2);

        // Should still exit normally
        assertTrue(adapter.exitCalled);
    }

    @Test
    @DisplayName("E2E: All exit command variants")
    void shouldHandleAllExitVariants() {
        // Test 'exit'
        TestOutputAdapter adapter1 = new TestOutputAdapter();
        adapter1.setInputs("exit");
        new Main(adapter1).run();
        assertTrue(adapter1.exitCalled);

        // Test 'quit'
        TestOutputAdapter adapter2 = new TestOutputAdapter();
        adapter2.setInputs("quit");
        new Main(adapter2).run();
        assertTrue(adapter2.exitCalled);

        // Test 'q'
        TestOutputAdapter adapter3 = new TestOutputAdapter();
        adapter3.setInputs("q");
        new Main(adapter3).run();
        assertTrue(adapter3.exitCalled);
    }

    @Test
    @DisplayName("E2E: Application should handle immediate exit")
    void shouldHandleImmediateExit() {
        TestOutputAdapter adapter = new TestOutputAdapter();
        adapter.setInputs("exit");

        Main app = new Main(adapter);
        app.run();

        assertTrue(adapter.welcomeCalled, "Should show welcome");
        assertTrue(adapter.exitCalled, "Should show exit");
        assertEquals(0, adapter.results.size(), "Should have no conversions");
    }

    /**
     * Test adapter that simulates user input and captures all output.
     */
    private static class TestOutputAdapter implements OutputAdapter {
        private List<String> inputs = new ArrayList<>();
        private int inputIndex = 0;

        List<String> results = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        List<String> successes = new ArrayList<>();
        List<String> infos = new ArrayList<>();
        boolean welcomeCalled = false;
        boolean helpCalled = false;
        boolean exitCalled = false;
        boolean blankLineCalled = false;

        void setInputs(String... inputs) {
            this.inputs = List.of(inputs);
            this.inputIndex = 0;
        }

        @Override
        public void showWelcome() {
            welcomeCalled = true;
        }

        @Override
        public String readInput(Scanner scanner) {
            if (inputIndex < inputs.size()) {
                return inputs.get(inputIndex++);
            }
            return "exit"; // Failsafe to prevent infinite loop
        }

        @Override
        public void showResult(String message) {
            results.add(message);
        }

        @Override
        public void showError(String message) {
            errors.add(message);
        }

        @Override
        public void showHelp() {
            helpCalled = true;
        }

        @Override
        public void showExit() {
            exitCalled = true;
        }

        @Override
        public void showBlankLine() {
            blankLineCalled = true;
        }

        @Override
        public void showSuccess(String message) {
            successes.add(message);
        }

        @Override
        public void showInfo(String message) {
            infos.add(message);
        }
    }
}
