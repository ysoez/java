package algorithm.sorting;

import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

interface IntArraySort {

    UnaryOperator<int[]> sortAlgorithm();

    @Test
    default void sort() {
        assertArrayEquals(new int[0], sortAlgorithm().apply(new int[]{}));
        assertArrayEquals(new int[]{7}, sortAlgorithm().apply(new int[]{7}));
        assertArrayEquals(new int[]{3, 7}, sortAlgorithm().apply(new int[]{7, 3}));
        assertArrayEquals(new int[]{1, 2, 3, 3, 4, 6, 7}, sortAlgorithm().apply(new int[]{7, 3, 1, 4, 6, 2, 3}));
    }

}
