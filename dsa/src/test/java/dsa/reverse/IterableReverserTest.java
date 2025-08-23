package dsa.reverse;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IterableReverserTest {

    private final IterableReverser<Integer> reverser = new IterableReverser<>();

    @Test
    void testReversesIterable() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        Iterable<Integer> result = reverser.apply(input);

        assertIterableEquals(Arrays.asList(5, 4, 3, 2, 1), result);
    }

    @Test
    void testEmptyIterable() {
        List<Integer> input = Collections.emptyList();
        Iterable<Integer> result = reverser.apply(input);

        assertSame(input, result);
        assertFalse(result.iterator().hasNext());
    }

    @Test
    void testNullIterable() {
        assertThrows(IllegalArgumentException.class, () -> reverser.apply(null));
    }

    @Test
    void testSingleElementIterable() {
        List<Integer> input = Collections.singletonList(42);
        Iterable<Integer> result = reverser.apply(input);

        assertIterableEquals(Collections.singletonList(42), result);
    }

    @Test
    void testGenericTypes() {
        IterableReverser<String> stringReverser = new IterableReverser<>();
        List<String> input = Arrays.asList("a", "b", "c");
        Iterable<String> result = stringReverser.apply(input);

        assertIterableEquals(Arrays.asList("c", "b", "a"), result);
    }

}