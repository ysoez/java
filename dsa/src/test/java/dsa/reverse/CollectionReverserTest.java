package dsa.reverse;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CollectionReverserTest {

    @Test
    void testReverseList() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Iterable<Integer> result = new CollectionReverser<Integer>().apply(input);
        assertIterableEquals(Arrays.asList(5, 4, 3, 2, 1), result);
    }

    @Test
    void testReverseQueue() {
        Queue<String> input = new ArrayDeque<>(Arrays.asList("a", "b", "c"));
        Iterable<String> result = new CollectionReverser<String>().apply(input);
        assertIterableEquals(Arrays.asList("c", "b", "a"), result);
    }

    @Test
    void testEmptyInput() {
        List<Integer> input = Collections.emptyList();
        Iterable<Integer> result = new CollectionReverser<Integer>().apply(input);
        assertSame(input, result);
        assertFalse(result.iterator().hasNext());
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new CollectionReverser<>().apply(null));
    }

    @Test
    void testSingleElement() {
        List<Integer> input = Collections.singletonList(42);
        Iterable<Integer> result = new CollectionReverser<Integer>().apply(input);
        assertIterableEquals(Collections.singletonList(42), result);
    }

}