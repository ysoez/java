package dsa.reverse;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class StackObjectReverserTest {

    @Test
    void testValidNonEmptyIntegerList() {
        List<Integer> input = Arrays.asList(1, 2, 3);
        var result = new StackObjectReverser<Integer>().apply(input);
        assertIterableEquals(List.of(3, 2, 1), result);
    }

    @Test
    void testSingleElementList() {
        List<Integer> input = List.of(42);
        var result = new StackObjectReverser<Integer>().apply(input);
        assertIterableEquals(List.of(42), result);
    }

    @Test
    void testEmptyList() {
        List<Object> input = Collections.emptyList();
        var result = new StackObjectReverser<>().apply(input);
        assertIterableEquals(Collections.emptyList(), result);
    }

    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new StackObjectReverser<>().apply(null));
    }

    @Test
    void testStringList() {
        List<String> input = Arrays.asList("a", "b", "c");
        var result = new StackObjectReverser<String>().apply(input);
        assertIterableEquals(Arrays.asList("c", "b", "a"), result);
    }

    @Test
    void testMixedTypeList() {
        List<Object> input = Arrays.asList(1, "two", 3.0);
        var result = new StackObjectReverser<>().apply(input);
        assertIterableEquals(Arrays.asList(3.0, "two", 1), result);
    }

    @Test
    void testLargeList() {
        List<Integer> input = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) input.add(i);
        var result = new StackObjectReverser<Integer>().apply(input);
        List<Integer> expected = new ArrayList<>();
        for (int i = 1000; i >= 1; i--) expected.add(i);
        assertIterableEquals(expected, result);
    }

    @Test
    void testListWithNullElements() {
        List<Object> input = Arrays.asList(null, 1, null);
        var result = new StackObjectReverser<>().apply(input);
        assertIterableEquals(Arrays.asList(null, 1, null), result);
    }

    @Test
    void testLinkedListInput() {
        List<Integer> input = new LinkedList<>(Arrays.asList(1, 2, 3));
        var result = new StackObjectReverser<Integer>().apply(input);
        assertIterableEquals(Arrays.asList(3, 2, 1), result);
    }

    @Test
    void testDuplicateElements() {
        List<Integer> input = Arrays.asList(1, 1, 2, 2);
        var result = new StackObjectReverser<Integer>().apply(input);
        assertIterableEquals(Arrays.asList(2, 2, 1, 1), result);
    }

}