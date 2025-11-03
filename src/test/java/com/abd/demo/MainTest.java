package com.abd.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Main Application Tests")
public class MainTest {

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
}
