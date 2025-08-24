package dsa.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class BoundedPriorityQueueTest extends BoundedQueueTest {

    abstract BoundedPriorityQueue<Integer> newQueue();

    @Test
    @Override
    void testEnqueueThenPeek() {
        var queue = newQueue();

        queue.enqueue(1);
        assertEquals(1, queue.peek());

        queue.enqueue(2);
        assertEquals(2, queue.peek());

        queue.poll();
        assertEquals(1, queue.peek());
    }

    @Test
    @Override
    void testEnqueueThenPoll() {
        var queue = newQueue();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);


        assertEquals(3, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(1, queue.poll());
    }

    @Test
    void testDescPriorityOrder() {
        var queue = newQueue();

        queue.enqueue(5);
        queue.enqueue(1);
        queue.enqueue(6);

        assertEquals(6, queue.poll());
        assertEquals(5, queue.poll());
        assertEquals(1, queue.poll());
    }

}