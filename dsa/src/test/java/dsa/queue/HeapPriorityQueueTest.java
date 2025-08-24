package dsa.queue;

class HeapPriorityQueueTest extends BoundedPriorityQueueTest {

    @Override
    BoundedPriorityQueue<Integer> newQueue() {
        return new HeapPriorityQueue(3);
    }

}