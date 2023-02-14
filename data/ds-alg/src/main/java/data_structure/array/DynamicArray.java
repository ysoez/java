package data_structure.array;

import util.Complexity;

import java.util.Objects;

import static data_structure.array.Arrays.checkIndex;

class DynamicArray<E> extends StaticArray<E> implements ResizableArray<E> {

    static final int DEFAULT_CAPACITY = 10;

    private int size;

    @Complexity(runtime = "O(1)", space = "O(1)")
    DynamicArray() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    @Complexity(runtime = "O(1)", space = "O(n)")
    DynamicArray(int capacity) {
        super(capacity);
    }

    @SafeVarargs
    @Complexity(runtime = "O(n)", space = "O(n)")
    DynamicArray(E... values) {
        super(values);
        this.size = values.length;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(n)")
    public void insertFirst(E value) {
        resizeIfFull();
        if (!isEmpty())
            for (int i = size; i > 0; i--)
                elements[i] = elements[i - 1];
        elements[0] = value;
        size++;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public void insertAt(int index, E value) {
        checkIndex(index, size);
        resizeIfFull();
        for (int i = size; i > index; i--)
            elements[i] = elements[i - 1];
        elements[index] = value;
        size++;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public void insertLast(E value) {
        resizeIfFull();
        elements[size++] = value;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public int size() {
        return size;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public int indexOf(E value) {
        for (int i = 0; i < size; i++)
            if (Objects.equals(elements[i], value))
                return i;
        return -1;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(n)")
    public void trimToSize() {
        var newArr = new Object[size];
        System.arraycopy(elements, 0, newArr, 0, size);
        this.elements = newArr;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Complexity(runtime = "O(n)", space = "O(1)")
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
    @Complexity(runtime = "O(n)", space = "O(1)")
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
    @Complexity(runtime = "O(1)", space = "O(1)")
    public E deleteLast() {
        throwIfEmpty();
        return (E) elements[--size];
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(n)")
    public String toString() {
        var builder = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            builder.append(elements[i]);
            if (i != size - 1)
                builder.append(", ");
        }
        return builder.append("]").toString();
    }

    @Complexity(runtime = "O(1)", space = "O(1)")
    private boolean isFull() {
        return elements.length == size;
    }

    @Complexity(runtime = "O(n)", space = "O(n)")
    private void resizeIfFull() {
        if (isFull())
            grow();
    }

    @SuppressWarnings("ManualArrayCopy")
    @Complexity(runtime = "O(n)", space = "O(n)")
    private void grow() {
        var newArr = size < 2 ? new Object[DEFAULT_CAPACITY]: new Object[size + size / 2];
        for (int i = 0; i < size; i++)
            newArr[i] = elements[i];
        this.elements = newArr;
    }

    @Complexity(runtime = "O(1)", space = "O(1)")
    private void throwIfEmpty() {
        if (isEmpty())
            throw new IllegalStateException("Array is empty");
    }

}
