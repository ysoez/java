package dsa.reverse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringReverserTest {

    private final Reverser<String> reverser = new StringReverser();

    @Test
    void testNormalString() {
        assertEquals("olleh", reverser.apply("hello"));
        assertEquals("dlrow", reverser.apply("world"));
    }

    @Test
    void testEmptyString() {
        assertEquals("", reverser.apply(""));
    }

    @Test
    void testSingleCharacter() {
        assertEquals("a", reverser.apply("a"));
    }

    @Test
    void testPalindrome() {
        assertEquals("madam", reverser.apply("madam"));
    }

    @Test
    void testStringWithSpaces() {
        assertEquals("dlrow olleh", reverser.apply("hello world"));
    }

    @Test
    void testNullInput() {
        assertNull(reverser.apply(null));
    }

    @Test
    void testStringWithSpecialChars() {
        assertEquals("!@#321", reverser.apply("123#@!"));
    }

}