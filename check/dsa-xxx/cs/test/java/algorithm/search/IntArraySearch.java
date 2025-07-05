package algorithm.search;

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

interface IntArraySearch {

    BiFunction<int[], Integer, Integer> searchAlgorithm();

    @Test
    default void search() {
        int[] numbers = {7, 1, 3, 6, 5};
        assertEquals(0, searchAlgorithm().apply(numbers, 7));
        assertEquals(-1, searchAlgorithm().apply(numbers, 70));
    }

}