package data_structure.queue;

import util.Complexity;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static data_structure.array.Arrays.checkCapacity;

class ArrayPriorityQueue<E extends Comparable<E>> implements Queue<E> {

    private Object[] elements;
    private int size;

    @Complexity(runtime = "O(1)", space = "O(n)")
    ArrayPriorityQueue(int capacity) {
        checkCapacity(capacity);
        elements = new Object[capacity];
    }

    @Override
    @SuppressWarnings("SingleStatementInBlock")
    public void enqueue(E value) {
        if (isFull())
            throw new IllegalStateException("Queue is full");
        if (value == null)
            elements[size] = value;
        else {
            int index = shiftItemsToInsert(value);
            elements[index] = value;
        }
        size++;
    }

    //TODO: highest number processed first
    @Override
    @SuppressWarnings("unchecked")
    public E dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        return (E) elements[--size];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        if (isEmpty())
            throw new NoSuchElementException();
        return (E) elements[size - 1];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return elements.length == size;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(elements, 0, size));
    }

    private int shiftItemsToInsert(E value) {
        int i;
        for (i = size - 1; i >= 0; i--) {
            @SuppressWarnings("unchecked") var current = (E) elements[i];
            if (value.compareTo(current) < 0)
                elements[i + 1] = elements[i];
            else
                break;
        }
        return i + 1;
    }

}
