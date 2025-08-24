package dsa.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class StaticArrayBoundedQueueTest extends BoundedQueueTest {

    @Override
    BoundedQueue<Integer> newQueue() {
        return new StaticArrayBoundedQueue<>(3);
    }

    @Test
    void testCircularArray() {
        var queue = new StaticArrayBoundedQueue<>(3);

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        queue.poll();
        queue.poll();

        assertDoesNotThrow(() -> queue.enqueue(4));
        assertDoesNotThrow(() -> queue.enqueue(5));
    }

}