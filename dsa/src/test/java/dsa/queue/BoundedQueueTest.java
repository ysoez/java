package dsa.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class BoundedQueueTest extends QueueTest {

    @Override
    abstract BoundedQueue<Integer> newQueue();

    @Test
    void testSize() {
        var queue = newQueue();

        queue.enqueue(1);
        assertEquals(1, queue.size());

        queue.enqueue(2);
        assertEquals(2, queue.size());

        queue.enqueue(3);
        assertEquals(3, queue.size());
    }

    @Test
    void testQueueIsFull() {
        var queue = newQueue();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertThrows(FullQueueException.class, () -> queue.enqueue(5));
    }

}