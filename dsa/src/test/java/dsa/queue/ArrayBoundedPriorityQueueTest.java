package dsa.queue;

class ArrayBoundedPriorityQueueTest extends BoundedPriorityQueueTest {

    @Override
    BoundedPriorityQueue<Integer> newQueue() {
        return new ArrayBoundedPriorityQueue<>(3);
    }

}