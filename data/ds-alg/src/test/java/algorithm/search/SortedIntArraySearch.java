package algorithm.search;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

interface SortedIntArraySearch {

    BiFunction<int[], Integer, Integer> searchAlgorithm();

    @Test
    default void search() {
        int[] numbers = {0};
        assertEquals(-1, searchAlgorithm().apply(numbers, 1));

        numbers = new int[]{1};
        assertEquals(-1, searchAlgorithm().apply(numbers, 3));
        assertEquals(0, searchAlgorithm().apply(numbers, 1));

        numbers = new int[]{1, 3};
        assertEquals(-1, searchAlgorithm().apply(numbers, 0));
        assertEquals(1, searchAlgorithm().apply(numbers, 3));

        numbers = new int[]{1, 3, 5, 6, 7};
        assertEquals(-1, searchAlgorithm().apply(numbers, 0));
        assertEquals(0, searchAlgorithm().apply(numbers, 1));
        assertEquals(2, searchAlgorithm().apply(numbers, 5));
        assertEquals(4, searchAlgorithm().apply(numbers, 7));
        assertEquals(-1, searchAlgorithm().apply(numbers, 70));
    }

}