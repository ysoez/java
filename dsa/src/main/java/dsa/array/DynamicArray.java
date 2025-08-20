package dsa.array;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.NoSuchElementException;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;
import static dsa.array.Arrays.checkCapacity;

public class DynamicArray<E> implements ResizableArray<E> {

    private StaticArray<E> elements;
    private int size;

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public DynamicArray() {
        this.elements = new StaticArray<>();
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public DynamicArray(int initialCapacity) {
        checkCapacity(initialCapacity);
        this.elements = new StaticArray<>(initialCapacity);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void set(int index, E value) {
        checkIndex(index);
        elements.set(index, value);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E get(int index) {
        checkIndex(index);
        return elements.get(index);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int length() {
        return elements.length();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void insertFirst(E value) {
        insertAt(0, value);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void insertAt(int index, E value) {
        checkIndexForAdd(index);
        growIfFull();
        shiftFrom(index);
        elements.set(index, value);
        size++;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void insertLast(E value) {
        growIfFull();
        elements.set(size++, value);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int size() {
        return size;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public int indexOf(E value) {
        for (int i = 0; i < size; i++)
            if (elements.get(i).equals(value))
                return i;
        return -1;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E deleteFirst() {
        return deleteAt(0);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E deleteAt(int index) {
        throwIfEmpty();
        checkIndex(index);
        var toRemove = elements.get(index);
        unshiftTo(index);
        size--;
        return toRemove;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E deleteLast() {
        throwIfEmpty();
        return elements.get(--size);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void trimToSize() {
        var arr = new StaticArray<E>(size);
        copyTo(arr);
        this.elements = arr;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException(index);
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    private void growIfFull() {
        if (isFull()) {
            var newArr = new StaticArray<E>(elements.length() * 2);
            copyTo(newArr);
            this.elements = newArr;
        }
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private boolean isFull() {
        return size == elements.length();
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    private void copyTo(StaticArray<E> newArr) {
        for (int i = 0; i < size; i++)
            newArr.set(i, elements.get(i));
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void throwIfEmpty() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    private void unshiftTo(int index) {
        for (int i = index; i < size; i++)
            elements.set(i, elements.get(i + 1));
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    private void shiftFrom(int index) {
        for (int i = size; i > index; i--)
            elements.set(i, elements.get(i - 1));
    }

}
