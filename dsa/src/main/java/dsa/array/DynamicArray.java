package dsa.array;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.NoSuchElementException;

import static dsa.Algorithm.Complexity.Value.CONSTANT;
import static dsa.array.Arrays.checkCapacity;
import static dsa.array.Arrays.checkIndex;

public class DynamicArray<E> implements ResizableArray<E> {

    private StaticArray<E> elements;
    private int size;

    public DynamicArray() {
        this.elements = new StaticArray<>();
    }

    public DynamicArray(int capacity) {
        checkCapacity(capacity);
        this.elements = new StaticArray<>(capacity);
    }

    @Override
    public void set(int index, E value) {
        checkIndex(index, size);
        elements.set(index, value);
    }

    @Override
    public E get(int index) {
        checkIndex(index, size);
        return elements.get(index);
    }

    @Override
    public int length() {
        return elements.length();
    }

    @Override
    public void insertFirst(E value) {

    }

    @Override
    public void insertAt(int index, E value) {
        checkIndex(index, size);
    }

    @Override
    public void insertLast(E value) {
        resizeOnDemand();
        elements.set(size++, value);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int indexOf(E value) {
        return 0;
    }

    @Override
    public E deleteFirst() {
        throwIfEmpty();
        return null;
    }

    @Override
    public E deleteAt(int index) {
        throwIfEmpty();
        return null;
    }

    @Override
    public E deleteLast() {
        throwIfEmpty();
        return elements.get(size--);
    }

    @Override
    public void trimToSize() {

    }

    private void resizeOnDemand() {
        if (isFull())
            elements = new StaticArray<>(elements.length() * 2);
    }

    private boolean isFull() {
        return size == elements.length();
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void throwIfEmpty() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

}
