package algorithm.sorting;

import org.junit.jupiter.api.Test;

import static algorithm.sorting.InsertionSort.base;
import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest {

    @Test
    void baseImplementation() {
        assertArrayEquals(new int[0], base(new int[]{}));
        assertArrayEquals(new int[]{7}, base(new int[]{7}));
        assertArrayEquals(new int[]{3, 7}, base(new int[]{7, 3}));
        assertArrayEquals(new int[]{1, 2, 3, 3, 4, 6, 7}, base(new int[]{7, 3, 1, 4, 6, 2, 3}));
    }

}