package dsa.tree.heap;

import static dsa.array.Arrays.swap;
import static dsa.tree.heap.Heaps.leftChildIndex;
import static dsa.tree.heap.Heaps.rightChildIndex;

public class MaxHeap implements Heap {

    private final int[] arr;
    private int size;

    public MaxHeap(int maxSize) {
        arr = new int[maxSize];
    }

    public MaxHeap() {
        arr = new int[10];
    }

    @Override
    public void insert(int value) {
        if (isFull())
            throw new IllegalStateException("FULL");
        arr[size++] = value;
        bubbleUp();
    }

    @Override
    public int remove() {
        throwIfEmpty();
        var root = arr[0];
        arr[0] = arr[--size];
        bubbleDown();
        return root;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isFull() {
        return arr.length == size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int max() {
        throwIfEmpty();
        return arr[0];
    }

    private void bubbleUp() {
        var index = size - 1;
        while (index > 0 && arr[index] > arr[parentIndex(index)]) {
            swap(arr, index, parentIndex(index));
            index = parentIndex(index);
        }
    }

    private void bubbleDown() {
        var index = 0;
        while (index <= size && !isValidParent(index)) {
            var largerChildIndex = largerChildIndex(index);
            swap(arr, index, largerChildIndex);
            index = largerChildIndex;
        }
    }

    private int largerChildIndex(int index) {
        //
        // ~ no left child = no children (complete tree)
        //
        if (!hasLeftChild(index)) {
            return index;
        }
        if (!hasRightChild(index)) {
            return leftChildIndex(index);
        }
        //
        // ~ node has left and right child
        //
        return leftChild(index) > rightChild(index) ? leftChildIndex(index) : rightChildIndex(index);
    }

    private boolean hasLeftChild(int index) {
        return leftChildIndex(index) <= size;
    }

    private boolean hasRightChild(int index) {
        return rightChildIndex(index) <= size;
    }

    private boolean isValidParent(int index) {
        //
        // ~ no left child = invalid (complete tree)
        //
        if (!hasLeftChild(index))
            return true;
        var parent = arr[index];
        var isValid = parent >= leftChild(index);
        if (hasRightChild(index))
            isValid &= parent >= rightChild(index);
        return isValid;
    }

    private int leftChild(int index) {
        return arr[leftChildIndex(index)];
    }

    private int rightChild(int index) {
        return arr[rightChildIndex(index)];
    }

    private int parentIndex(int index) {
        return (index - 1) / 2;
    }

    private void throwIfEmpty() {
        if (isEmpty())
            throw new EmptyHeapException();
    }

}
