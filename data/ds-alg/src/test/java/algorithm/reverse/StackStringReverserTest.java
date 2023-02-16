package algorithm.reverse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackStringReverserTest {

    @Test
    void reverse() {
        assertEquals("edcba", new StackStringReverser().reverse("abcde"));
    }

}
