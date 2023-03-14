package data_structure.queue;

import java.util.Comparator;
import java.util.PriorityQueue;

class HeapPriorityQueue<E extends Comparable<E>> implements Queue<E> {

    private final PriorityQueue<E> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

    @Override
    public void enqueue(E value) {
        maxHeap.add(value);
    }

    @Override
    public E dequeue() {
        return maxHeap.remove();
    }

    @Override
    public E peek() {
        return maxHeap.peek();
    }

    @Override
    public boolean isEmpty() {
        return maxHeap.isEmpty();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public String toString() {
        return maxHeap.toString();
    }

}
