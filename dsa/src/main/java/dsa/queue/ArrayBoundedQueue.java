package dsa.queue;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;
import dsa.array.Arrays;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;

class ArrayBoundedQueue<E> implements BoundedQueue<E> {

    private final E[] elements;
    private int front;
    private int rear;
    private int size;

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = LINEAR))
    ArrayBoundedQueue(int maxSize) {
        elements = Arrays.newArray(maxSize);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void enqueue(E e) {
        if (isFull())
            throw new FullQueueException();
        elements[rear] = e;
        rear = nextIndex(rear);
        size++;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E poll() {
        if (isEmpty())
            throw new EmptyQueueException();
        var polled = elements[front];
        front = nextIndex(front);
        size--;
        return polled;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E peek() {
        if (isEmpty())
            throw new EmptyQueueException();
        return elements[front];
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int size() {
        return size;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isFull() {
        return size == elements.length;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private int nextIndex(int index) {
        return (index + 1) % elements.length;
    }

}
