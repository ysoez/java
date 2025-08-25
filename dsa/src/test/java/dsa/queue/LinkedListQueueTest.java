package dsa.queue;

class LinkedListQueueTest extends QueueTest {

    @Override
    Queue<Integer> newQueue() {
        return new LinkedListQueue<>();
    }

}