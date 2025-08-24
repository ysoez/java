package dsa.reverse;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class QueueReverserTest {

    private final QueueReverser<String> reverser = new QueueReverser<>();

    @Test
    void testReverseQueue() {
        Queue<String> input = new ArrayDeque<>(Arrays.asList("a", "b", "c"));
        Iterable<String> result = new QueueReverser<String>().apply(input);
        assertIterableEquals(Arrays.asList("c", "b", "a"), result);
    }

    @Test
    void testEmptyInput() {
        Queue<String> input = new ArrayDeque<>();
        Queue<String> result = reverser.apply(input);
        assertSame(input, result);
        assertFalse(result.iterator().hasNext());
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> reverser.apply(null));
    }

    @Test
    void testSingleElement() {
        Queue<String> input = new ArrayDeque<>(List.of("a"));
        Queue<String> result = reverser.apply(input);
        assertEquals(input, result);
    }

}