package data_structure.queue;

import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.NoSuchElementException;

import static data_structure.array.Arrays.checkCapacity;
import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;

class ArrayQueue<E> implements Queue<E> {

    private Object[] elements;
    private int front;
    private int rear;
    private int size;

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = LINEAR))
    ArrayQueue(int capacity) {
        checkCapacity(capacity);
        elements = new Object[capacity];
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void enqueue(E value) {
        if (isFull())
            throw new IllegalStateException("Queue is full");
        elements[rear] = value;
        rear = nextCircularIndex(rear);
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
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
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E peek() {
        if (isFull())
            throw new NoSuchElementException();
        return (E) elements[front];
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isFull() {
        return elements.length == size;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private int nextCircularIndex(int index) {
        return index + 1 % elements.length;
    }

}
