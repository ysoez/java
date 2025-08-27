package dsa.queue;

import dsa.graph.tree.heap.EmptyHeapException;
import dsa.graph.tree.heap.MaxHeap;

class HeapPriorityQueue implements BoundedPriorityQueue<Integer> {

    private final MaxHeap heap;

    HeapPriorityQueue(int maxSize) {
        heap = new MaxHeap(maxSize);
    }

    @Override
    public void enqueue(Integer val) {
        try {
            heap.insert(val);
        } catch (IllegalStateException e) {
            throw new FullQueueException();
        }
    }

    @Override
    public Integer poll() {
        try {
            return heap.remove();
        } catch (EmptyHeapException e) {
            throw new EmptyQueueException();
        }
    }

    @Override
    public Integer peek() {
        // causes test to fail
        throw new UnsupportedOperationException();
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
