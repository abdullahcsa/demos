package com.abd.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testGetMessage() {
        String expected = "Hello, World!";
        String actual = Main.getMessage();
        assertEquals(expected, actual, "getMessage should return 'Hello, World!'");
    }

    @Test
    public void testMainClassExists() {
        assertNotNull(Main.class, "Main class should exist");
    }

    @Test
    public void testMainClassHasMainMethod() {
        try {
            Main.class.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            fail("Main class should have a main method");
        }
    }
}
