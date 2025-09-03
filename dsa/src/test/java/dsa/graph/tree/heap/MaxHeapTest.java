package dsa.graph.tree.heap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxHeapTest {

    @Test
    void testIsEmpty() {
        var heap = new MaxHeap<Integer>();
        assertTrue(heap.isEmpty());

        heap.insert(5);
        assertFalse(heap.isEmpty());

        heap.remove();
        assertTrue(heap.isEmpty());
    }

    @Test
    void testInsertAndMax() {
        var heap = new MaxHeap<Integer>(5);

        heap.insert(10);
        assertArrayEquals(new Integer[]{10, null, null, null, null}, heap.elements);

        heap.insert(5);
        assertArrayEquals(new Integer[]{10, 5, null, null, null}, heap.elements);

        heap.insert(17);
        assertArrayEquals(new Integer[]{17, 5, 10, null, null}, heap.elements);

        heap.insert(4);
        assertArrayEquals(new Integer[]{17, 5, 10, 4, null}, heap.elements);

        heap.insert(22);
        assertArrayEquals(new Integer[]{22, 17, 10, 4, 5}, heap.elements);

        assertEquals(5, heap.size());
        assertEquals(22, heap.max());
    }

    @Test
    void testInsertIfFull() {
        var heap = new MaxHeap<Integer>(3);
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);

        assertThrows(FullHeapException.class, () -> heap.insert(4));
    }

    @Test
    void testRemoveFromEmptyHeapThrowsException() {
        var heap = new MaxHeap<Integer>();
        assertThrows(IllegalStateException.class, heap::remove);
    }

    @Test
    void testRemove() {
        var heap = new MaxHeap<Integer>(6);

        heap.insert(10);
        heap.insert(5);
        heap.insert(17);
        heap.insert(4);
        heap.insert(22);
        assertArrayEquals(new Integer[]{22, 17, 10, 4, 5, null}, heap.elements);
        assertEquals(5, heap.size());

        assertEquals(22, heap.remove());
        assertEquals(17, heap.max());
        assertEquals(4, heap.size());
        assertArrayEquals(new Integer[]{17, 5, 10, 4, 5, null}, heap.elements);

        assertEquals(17, heap.remove());
        assertEquals(10, heap.max());
        assertEquals(3, heap.size());
        assertArrayEquals(new Integer[]{10, 5, 4, 4, 5, null}, heap.elements);

        assertEquals(10, heap.remove());
        assertEquals(2, heap.size());

        assertEquals(5, heap.remove());
        assertEquals(1, heap.size());

        assertEquals(4, heap.remove());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testMaxFromEmptyHeapThrowsException() {
        var heap = new MaxHeap<Integer>();
        assertThrows(EmptyHeapException.class, heap::max);
    }

}