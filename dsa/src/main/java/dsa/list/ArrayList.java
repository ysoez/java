package dsa.list;

import dsa.array.DynamicArray;

class ArrayList<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private final DynamicArray<E> array;

    ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    ArrayList(int initialCapacity) {
        this.array = new DynamicArray<>(initialCapacity);
    }

    @Override
    public void insertFirst(E value) {
        array.insertFirst(value);
    }

    @Override
    public void insertAt(int index, E value) {
        array.insertAt(index, value);
    }

    @Override
    public void insertLast(E value) {
        array.insertLast(value);
    }

    @Override
    public void set(int index, E value) {
        array.set(index, value);
    }

    @Override
    public E get(int index) {
        return array.get(index);
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public int indexOf(E value) {
        return array.indexOf(value);
    }

    @Override
    public E deleteFirst() {
        return array.deleteFirst();
    }

    @Override
    public E deleteAt(int index) {
        return array.deleteAt(index);
    }

    @Override
    public E deleteLast() {
        return array.deleteLast();
    }

}
