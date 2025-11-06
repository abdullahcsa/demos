package com.abd.demo;

import com.abd.demo.adapter.OutputAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Main Application Tests")
public class MainTest {

    private TestOutputAdapter testAdapter;
    private Main main;

    @BeforeEach
    void setUp() {
        testAdapter = new TestOutputAdapter();
        main = new Main(testAdapter);
    }

    @Test
    @DisplayName("Should return 'Hello, World!' message")
    public void testGetMessage() {
        String expected = "Hello, World!";
        String actual = Main.getMessage();
        assertEquals(expected, actual, "getMessage should return 'Hello, World!'");
    }

    @Test
    @DisplayName("Main class should exist")
    public void testMainClassExists() {
        assertNotNull(Main.class, "Main class should exist");
    }

    @Test
    @DisplayName("Main class should have main method")
    public void testMainClassHasMainMethod() {
        try {
            Main.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            fail("Main class should have a main method");
        }
    }

    @Test
    @DisplayName("Should construct Main with default OutputAdapter")
    public void testDefaultConstructor() {
        Main defaultMain = new Main();
        assertNotNull(defaultMain);
    }

    @Test
    @DisplayName("Should construct Main with custom OutputAdapter")
    public void testConstructorWithAdapter() {
        assertNotNull(main);
    }

    /**
     * Test OutputAdapter implementation that captures all method calls
     * for verification in tests.
     */
    private static class TestOutputAdapter implements OutputAdapter {
        boolean welcomeCalled = false;
        boolean helpCalled = false;
        boolean exitCalled = false;
        boolean blankLineCalled = false;
        List<String> results = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        List<String> inputs = new ArrayList<>();
        int inputIndex = 0;

        void setInputs(String... inputLines) {
            this.inputs = List.of(inputLines);
            this.inputIndex = 0;
        }

        @Override
        public void showWelcome() {
            welcomeCalled = true;
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
        public void showResult(String result) {
            if (result != null) {
                results.add(result);
            }
        }

        @Override
        public void showError(String error) {
            errors.add(error);
        }

        @Override
        public void showBlankLine() {
            blankLineCalled = true;
        }

        @Override
        public String readInput(Scanner scanner) {
            if (inputIndex < inputs.size()) {
                return inputs.get(inputIndex++);
            }
            return "exit"; // Default to exit to prevent infinite loop
        }
    }
}
