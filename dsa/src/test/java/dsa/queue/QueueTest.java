package dsa.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class QueueTest {

    abstract Queue<Integer> newQueue();

    @Test
    void testIsEmpty() {
        var queue = newQueue();
        assertTrue(queue.isEmpty());

        queue.enqueue(1);
        assertFalse(queue.isEmpty());

        queue.peek();
        assertTrue(queue.isEmpty());
    }

    @Test
    void testPeekEmptyQueue() {
        var queue = newQueue();
        assertThrows(EmptyQueueException.class, queue::peek);
    }

    @Test
    void testPollEmptyQueue() {
        var queue = newQueue();
        assertThrows(EmptyQueueException.class, queue::poll);
    }

    @Test
    void testEnqueueThenPeek() {
        var queue = newQueue();

        queue.enqueue(1);
        assertEquals(1, queue.peek());

        queue.enqueue(2);
        assertEquals(2, queue.peek());

        queue.enqueue(3);
        assertEquals(3, queue.peek());
    }

    @Test
    void testEnqueueThenPoll() {
        var queue = newQueue();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);


        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(3, queue.poll());
    }

}