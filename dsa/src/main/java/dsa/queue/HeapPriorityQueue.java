package dsa.queue;

import dsa.graph.tree.heap.EmptyHeapException;
import dsa.graph.tree.heap.MaxHeap;

class HeapPriorityQueue<E extends Comparable<E>> implements BoundedPriorityQueue<E> {

    private final MaxHeap<E> heap;

    HeapPriorityQueue(int maxSize) {
        heap = new MaxHeap<>(maxSize);
    }

    @Override
    public void enqueue(E val) {
        try {
            heap.insert(val);
        } catch (IllegalStateException e) {
            throw new FullQueueException();
        }
    }

    @Override
    public E poll() {
        try {
            return heap.remove();
        } catch (EmptyHeapException e) {
            throw new EmptyQueueException();
        }
    }

    @Override
    public E peek() {
        try {
            return heap.max();
        } catch (EmptyHeapException e) {
            throw new EmptyQueueException();
        }
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isFull() {
        return heap.isFull();
    }

}
