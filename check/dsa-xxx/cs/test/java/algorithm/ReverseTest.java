package algorithm;

import algorithm.Reverse;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReverseTest {

    @Test
    void reverseString() {
        assertEquals("edcba", Reverse.string("abcde"));
        assertEquals("olleh", Reverse.string("hello"));
    }

    @Test
    void reverseQueue() {
        assertArrayEquals(
            new ArrayDeque<>(List.of(30, 20, 10)).toArray(),
            Reverse.queue(new ArrayDeque<>(List.of(10, 20, 30))).toArray()
        );
    }

    @Test
    void reverseIntArray() {
        assertThrows(IllegalArgumentException.class, () -> Reverse.intArray(null));
        assertArrayEquals(new int[0], Reverse.intArray(new int[0]));
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, Reverse.intArray(new int[]{1, 2, 3, 4, 5}));
        assertArrayEquals(new int[]{40, 30, 20, 10}, Reverse.intArray(new int[]{10, 20, 30, 40}));
    }

}
