package dsa.queue;

import dsa.array.Arrays;

class StaticArrayBoundedQueue<E> implements BoundedQueue<E> {

    private final E[] elements;
    private int front;
    private int rear;
    private int size;

    StaticArrayBoundedQueue(int maxSize) {
        elements = Arrays.newArray(maxSize);
    }

    @Override
    public void enqueue(E e) {
        if (isFull())
            throw new FullQueueException();
        elements[rear] = e;
        rear = nextIndex(rear);
        size++;
    }

    @Override
    public E poll() {
        if (isEmpty())
            throw new EmptyQueueException();
        var polled = elements[front];
        front = nextIndex(front);
        size--;
        return polled;
    }

    @Override
    public E peek() {
        if (isEmpty())
            throw new EmptyQueueException();
        return elements[front];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isFull() {
        return size == elements.length;
    }

    private int nextIndex(int index) {
        return (index + 1) % elements.length;
    }

}
