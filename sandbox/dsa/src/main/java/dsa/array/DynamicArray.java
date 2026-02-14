package dsa.array;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;

public class DynamicArray<E> implements ResizableArray<E> {

    private StaticArray<E> array;
    private int size;

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public DynamicArray() {
        this.array = new StaticArray<>();
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public DynamicArray(int initialCapacity) {
        this.array = new StaticArray<>(initialCapacity);
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
        array.set(index, value);
        size++;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void insertLast(E value) {
        growIfFull();
        array.set(size++, value);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void set(int index, E value) {
        checkIndex(index);
        array.set(index, value);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E get(int index) {
        checkIndex(index);
        return array.get(index);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E getFromEnd(int offset) {
        if (offset > size)
            throw new IllegalArgumentException();
        return offset > 0 ? array.get(size - offset) : array.get(size - 1);
    }

    @Override
    public Collection<E> getMiddle() {
        return List.of();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int length() {
        return array.length();
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
            if (Objects.equals(array.get(i), value))
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
        var toRemove = array.get(index);
        unshiftTo(index);
        size--;
        return toRemove;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E deleteLast() {
        throwIfEmpty();
        return array.get(--size);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public void clear() {
        for (int i = 0; i < size; i++)
            array.set(i, null);
        size = 0;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public E[] toArray() {
        E[] arr = Arrays.newArray(size);
        for (int i = 0; i < size; i++)
            arr[i] = array.get(i);
        return arr;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void trimToSize() {
        var newArr = new StaticArray<E>(size);
        copyTo(newArr);
        this.array = newArr;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    private void growIfFull() {
        if (isFull())
            grow();
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private boolean isFull() {
        return array.length() == size;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    private void grow() {
        var newArray = new StaticArray<E>(size * 3 / 2);
        copyTo(newArray);
        this.array = newArray;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    private void copyTo(StaticArray<E> newArr) {
        for (int i = 0; i < size; i++)
            newArr.set(i, array.get(i));
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    private void shiftFrom(int index) {
        for (int i = size; i > index; i--)
            array.set(i, array.get(i - 1));
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    private void unshiftTo(int index) {
        for (int i = index; i < size; i++)
            array.set(index, array.get(index + 1));
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException(index);
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size)
            throw new ArrayIndexOutOfBoundsException();
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void throwIfEmpty() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

}
