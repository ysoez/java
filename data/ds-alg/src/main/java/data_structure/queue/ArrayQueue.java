package data_structure.queue;

import util.Complexity;

import java.util.NoSuchElementException;

import static data_structure.array.Arrays.checkCapacity;

class ArrayQueue<E> implements Queue<E> {

    private Object[] elements;
    private int front;
    private int rear;
    private int size;

    @Complexity(runtime = "O(1)", space = "O(n)")
    ArrayQueue(int capacity) {
        checkCapacity(capacity);
        elements = new Object[capacity];
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public void enqueue(E value) {
        if (isFull())
            throw new IllegalStateException("Queue is full");
        elements[rear] = value;
        rear = nextCircularIndex(rear);
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Complexity(runtime = "O(1)", space = "O(1)")
    public E dequeue() {
        if (isFull())
            throw new NoSuchElementException();
        elements[front] = null;
        E element = (E) elements[front];
        front = nextCircularIndex(front);
        return element;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Complexity(runtime = "O(1)", space = "O(1)")
    public E peek() {
        if (isFull())
            throw new NoSuchElementException();
        return (E) elements[front];
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public boolean isFull() {
        return elements.length == size;
    }

    @Complexity(runtime = "O(1)", space = "O(1)")
    private int nextCircularIndex(int index) {
        return index + 1 % elements.length;
    }

}
