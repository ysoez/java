package dsa.tree.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeapTest {

    private Heap heap;

    @BeforeEach
    public void setup() {
        heap = new MaxHeap();
    }

    @Test
    public void testInsertAndMax() {
        heap.insert(10);
        heap.insert(25);
        heap.insert(5);

        assertEquals(3, heap.size());
        assertEquals(25, heap.max());
    }

    @Test
    public void testRemove() {
        heap.insert(15);
        heap.insert(40);
        heap.insert(30);

        assertEquals(40, heap.max());
        heap.remove(); // removes max (40)
        assertEquals(30, heap.max());
        assertEquals(2, heap.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(heap.isEmpty());

        heap.insert(5);
        assertFalse(heap.isEmpty());

        heap.remove();
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testIsFull() {
        for (int i = 1; i <= 10; i++)
            heap.insert(i);
        assertTrue(heap.isFull());
    }

    @Test
    public void testRemoveUntilEmpty() {
        heap.insert(100);
        heap.insert(50);
        heap.insert(75);

        heap.remove(); // 100
        heap.remove(); // 75
        heap.remove(); // 50

        assertTrue(heap.isEmpty());
    }

    @Test
    public void testInsertBeyondCapacityThrowsException() {
        for (int i = 1; i <= 10; i++)
            heap.insert(i);
        assertThrows(IllegalStateException.class, () -> heap.insert(11));
    }

    @Test
    public void testRemoveFromEmptyThrowsException() {
        assertThrows(IllegalStateException.class, heap::remove);
    }

    @Test
    public void testMaxOnEmptyThrowsException() {
        assertThrows(IllegalStateException.class, heap::max);
    }
}

