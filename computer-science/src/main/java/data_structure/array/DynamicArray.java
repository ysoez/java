package data_structure.array;

import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.Objects;

import static data_structure.array.Arrays.checkIndex;
import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;

class DynamicArray<E> extends StaticArray<E> implements ResizableArray<E> {

    static final int DEFAULT_CAPACITY = 10;

    private int size;

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    DynamicArray() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = LINEAR))
    DynamicArray(int capacity) {
        super(capacity);
    }

    @SafeVarargs
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    DynamicArray(E... values) {
        super(values);
        this.size = values.length;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void insertFirst(E value) {
        resizeIfFull();
        if (!isEmpty())
            for (int i = size; i > 0; i--)
                elements[i] = elements[i - 1];
        elements[0] = value;
        size++;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void insertAt(int index, E value) {
        checkIndex(index, size);
        resizeIfFull();
        for (int i = size; i > index; i--)
            elements[i] = elements[i - 1];
        elements[index] = value;
        size++;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void insertLast(E value) {
        resizeIfFull();
        elements[size++] = value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int size() {
        return size;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public int indexOf(E value) {
        for (int i = 0; i < size; i++)
            if (Objects.equals(elements[i], value))
                return i;
        return -1;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void trimToSize() {
        var newArr = new Object[size];
        System.arraycopy(elements, 0, newArr, 0, size);
        this.elements = newArr;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E deleteFirst() {
        throwIfEmpty();
        Object elementToRemove = elements[0];
        for (int i = 0; i < size - 1; i++)
            elements[i] = elements[i + 1];
        size--;
        return (E) elementToRemove;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E deleteAt(int index) {
        throwIfEmpty();
        checkIndex(index, size);
        Object elementToRemove = elements[index];
        for (int i = index; i < size - 1; i++)
            elements[i] = elements[i + 1];
        size--;
        return (E) elementToRemove;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E deleteLast() {
        throwIfEmpty();
        return (E) elements[--size];
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public String toString() {
        var builder = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            builder.append(elements[i]);
            if (i != size - 1)
                builder.append(", ");
        }
        return builder.append("]").toString();
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private boolean isFull() {
        return elements.length == size;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    private void resizeIfFull() {
        if (isFull())
            grow();
    }

    @SuppressWarnings("ManualArrayCopy")
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    private void grow() {
        var newArr = size < 2 ? new Object[DEFAULT_CAPACITY]: new Object[size + size / 2];
        for (int i = 0; i < size; i++)
            newArr[i] = elements[i];
        this.elements = newArr;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void throwIfEmpty() {
        if (isEmpty())
            throw new IllegalStateException("Array is empty");
    }

}
