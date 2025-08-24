package dsa.queue;

class StackQueueTest extends QueueTest{

    @Override
    Queue<Integer> newQueue() {
        return new StackQueue<>();
    }

}