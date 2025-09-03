package dsa.graph.tree.heap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapsTest {

    @Test
    void testMaxHeapOf() {
        var input = new int[]{5, 3, 8, 4, 1, 2};

        Heaps.maxHeapOf(input);

        assertArrayEquals(new int[]{8, 4, 5, 3, 1, 2}, input);
    }

    @Test
    void testGetKthLargest() {
        var arr = new int[]{5, 3, 8, 4, 1, 2};

        assertEquals(8, Heaps.getKthLargest(arr, 1));
        assertEquals(5, Heaps.getKthLargest(arr, 2));
        assertEquals(4, Heaps.getKthLargest(arr, 3));
        assertEquals(3, Heaps.getKthLargest(arr, 4));
        assertEquals(2, Heaps.getKthLargest(arr, 5));
        assertEquals(1, Heaps.getKthLargest(arr, 6));

        assertThrows(IllegalArgumentException.class, () -> Heaps.getKthLargest(arr, -1));
        assertThrows(IllegalArgumentException.class, () -> Heaps.getKthLargest(arr, 0));
        assertThrows(IllegalArgumentException.class, () -> Heaps.getKthLargest(arr, 7));
    }

}