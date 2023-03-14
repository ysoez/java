package data_structure.tree.heap;

import java.util.NoSuchElementException;

import static data_structure.array.Arrays.checkCapacity;
import static java.lang.Math.max;

class MaxArrayHeap<E extends Comparable<E>> implements Heap<E> {

    private final Object[] elements;
    private int size;

    MaxArrayHeap(int capacity) {
        checkCapacity(capacity);
        this.elements = new Object[capacity];
    }

    @Override
    public void insert(E value) {
        if (isFull())
            throw new IllegalStateException("Heap is full");
        elements[size++] = value;
        bubbleUp();
    }

    @Override
    public E remove() {
        if (isEmpty())
            throw new NoSuchElementException();
        E root = readAt(0);
        elements[0] = elements[--size];
        bubbleDown();
        return root;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public E max() {
        return readAt(0);
    }

    private boolean isFull() {
        return size == elements.length;
    }

    private void bubbleUp() {
        var index = size - 1;
        while (index > 0 && readAt(index).compareTo(parent(index)) > 0) {
            swap(index, parentIndex(index));
            index = parentIndex(index);
        }
    }

    private void bubbleDown() {
        var index = 0;
        while (index <= size && !isValidParent(index)) {
            var largerChildIndex = largerChildIndex(index);
            swap(index, largerChildIndex);
            index = largerChildIndex;
        }
    }

    private void swap(int firstIndex, int secondIndex) {
        var tmp = elements[firstIndex];
        elements[firstIndex] = elements[secondIndex];
        elements[secondIndex] = tmp;
    }

    private boolean isValidParent(int index) {
        if (!hasLeftChild(index))
            return true;
        var isValid = readAt(index).compareTo(leftChild(index)) >= 0;
        if (hasRightChild(index))
            isValid &= readAt(index).compareTo(rightChild(index)) >= 0;
        return isValid;
    }

    private int largerChildIndex(int index) {
        if (!hasLeftChild(index))
            return index;
        if (!hasRightChild(index))
            return leftChildIndex(index);
        return Math.max(leftChildIndex(index), rightChildIndex(index));
    }

    private E parent(int index) {
        return readAt(parentIndex(index));
    }

    private int parentIndex(int index) {
        return (index - 1) / 2;
    }

    private E leftChild(int index) {
        return readAt(leftChildIndex(index));
    }

    private int leftChildIndex(int index) {
        return index * 2 + 1;
    }

    private E rightChild(int index) {
        return readAt(rightChildIndex(index));
    }

    private int rightChildIndex(int index) {
        return index * 2 + 2;
    }

    private boolean hasLeftChild(int index) {
        return leftChildIndex(index) <= size;
    }

    private boolean hasRightChild(int index) {
        return rightChildIndex(index) <= size;
    }

    @SuppressWarnings("unchecked")
    private E readAt(int index) {
        return (E) elements[index];
    }

}
