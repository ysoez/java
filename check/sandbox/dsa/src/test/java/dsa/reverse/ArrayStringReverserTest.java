package dsa.reverse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStringReverserTest {

    private final Reverser<String> reverser = new ArrayStringReverser();

    @Test
    void testValidNonEmptyString() {
        assertEquals("olleh", reverser.apply("hello"));
    }

    @Test
    void testSingleCharacterString() {
        assertEquals("a", reverser.apply("a"));
    }

    @Test
    void testEmptyString() {
        assertEquals("", reverser.apply(""));
    }

    @Test
    void testBlankString() {
        assertEquals("   ", reverser.apply("   "));
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> reverser.apply(null));
    }

    @Test
    void testSpecialCharacters() {
        assertEquals("#@!", reverser.apply("!@#"));
    }

    @Test
    void testNumericString() {
        assertEquals("54321", reverser.apply("12345"));
    }

    @Test
    void testLongString() {
        String input = "abcdefghijklmnopqrstuvwxyz";
        String expected = "zyxwvutsrqponmlkjihgfedcba";
        assertEquals(expected, reverser.apply(input));
    }

    @Test
    void testMixedCaseString() {
        assertEquals("oLLeH", reverser.apply("HeLLo"));
    }

    @Test
    void testUnicodeString() {
        assertEquals("はちにんこ", reverser.apply("こんにちは"));
    }

}