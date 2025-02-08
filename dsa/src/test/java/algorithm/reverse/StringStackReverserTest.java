package algorithm.reverse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringStackReverserTest {

    @Test
    void reverseStringTest() {
        var reverser = new StringStackReverser();
        assertThrows(IllegalArgumentException.class, () -> reverser.apply(null));
        assertEquals("", reverser.apply(""));
        assertEquals("cba", reverser.apply("abc"));
    }

}