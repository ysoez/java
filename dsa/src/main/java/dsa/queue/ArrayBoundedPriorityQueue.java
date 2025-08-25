package dsa.queue;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;

class ArrayBoundedPriorityQueue<E extends Comparable<E>> implements BoundedPriorityQueue<E> {

    private final E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = LINEAR))
    ArrayBoundedPriorityQueue(int maxSize) {
        elements = (E[]) java.lang.reflect.Array.newInstance(Comparable.class, maxSize);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public void enqueue(E e) {
        if (isFull())
            throw new FullQueueException();
        int i = shiftElementsToInsert(e);
        elements[i] = e;
        size++;
    }


    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E poll() {
        if (isEmpty())
            throw new EmptyQueueException();
        //
        // ~ larger first
        //
        return elements[--size];
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E peek() {
        if (isEmpty())
            throw new EmptyQueueException();
        return elements[size - 1];
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

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    private int shiftElementsToInsert(E e) {
        int i;
        for (i = size - 1; i >= 0; i--)
            if (elements[i].compareTo(e) > 0)
                elements[i + 1] = elements[i];
            else
                break;
        return i + 1;
    }

}
