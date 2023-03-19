package algorithm.sorting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {

    @Test
    void baseImplementation() {
        assertArrayEquals(new int[0], BubbleSort.base(new int[]{}));
        assertArrayEquals(new int[]{7}, BubbleSort.base(new int[]{7}));
        assertArrayEquals(new int[]{3, 7}, BubbleSort.base(new int[]{7, 3}));
        assertArrayEquals(new int[]{1, 2, 3, 3, 4, 6, 7}, BubbleSort.base(new int[]{7, 3, 1, 4, 6, 2, 3}));
    }

    @Test
    void optimizedSortCheck() {
        assertArrayEquals(new int[0], BubbleSort.sortCheck(new int[]{}));
        assertArrayEquals(new int[]{7}, BubbleSort.sortCheck(new int[]{7}));
        assertArrayEquals(new int[]{3, 7}, BubbleSort.sortCheck(new int[]{7, 3}));
        assertArrayEquals(new int[]{1, 2, 3, 3, 4, 6, 7}, BubbleSort.sortCheck(new int[]{7, 3, 1, 4, 6, 2, 3}));
    }

    @Test
    void optimizedReducedNestedIterations() {
        assertArrayEquals(new int[0], BubbleSort.reducedNestedIterations(new int[]{}));
        assertArrayEquals(new int[]{7}, BubbleSort.reducedNestedIterations(new int[]{7}));
        assertArrayEquals(new int[]{3, 7}, BubbleSort.reducedNestedIterations(new int[]{7, 3}));
        assertArrayEquals(new int[]{1, 2, 3, 3, 4, 6, 7}, BubbleSort.reducedNestedIterations(new int[]{7, 3, 1, 4, 6, 2, 3}));
    }

}