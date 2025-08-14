package dsa.queue;

import dsa.tree.heap.MaxHeap;

// heap wrapper to provide queue interface
public class HeapPriorityQueue {

    private MaxHeap heap = new MaxHeap();

    void enqueue(int val) {
        heap.insert(val);
    }

    void dequeue() {
        heap.remove();
    }

    boolean isEmpty() {
        return heap.isEmpty();
    }

}
