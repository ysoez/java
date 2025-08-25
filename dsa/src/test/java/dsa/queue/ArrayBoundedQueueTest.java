package dsa.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ArrayBoundedQueueTest extends BoundedQueueTest {

    @Override
    BoundedQueue<Integer> newQueue() {
        return new ArrayBoundedQueue<>(3);
    }

    @Test
    void testCircularArray() {
        var queue = new ArrayBoundedQueue<>(3);

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        queue.poll();
        queue.poll();

        assertDoesNotThrow(() -> queue.enqueue(4));
        assertDoesNotThrow(() -> queue.enqueue(5));
    }

}