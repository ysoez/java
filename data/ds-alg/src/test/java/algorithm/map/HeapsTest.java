package algorithm.map;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeapsTest {

    @Test
    void heapify() {
        int[] numbers = {5, 3, 8, 4, 1, 2};
        Heaps.heapifyEfficienly(numbers);
        assertArrayEquals(new int[]{8, 4, 5, 3, 1, 2}, numbers);

        numbers = new int[]{5, 3, 8, 4, 1, 2};
        Heaps.heapify(numbers);
        assertArrayEquals(new int[]{8, 4, 5, 3, 1, 2}, numbers);
    }

    @Test
    void testGetKthLargest() {
        int[] numbers = {5, 3, 8, 4, 1, 2};

        assertThrows(IllegalArgumentException.class, () -> Heaps.getKthLargest(numbers, 0));
        assertThrows(IllegalArgumentException.class, () -> Heaps.getKthLargest(numbers, 7));

        assertEquals(8, Heaps.getKthLargest(numbers, 1));
        assertEquals(5, Heaps.getKthLargest(numbers, 2));
        assertEquals(4, Heaps.getKthLargest(numbers, 3));
        assertEquals(4, Heaps.getKthLargest(numbers, 6));
    }

}
