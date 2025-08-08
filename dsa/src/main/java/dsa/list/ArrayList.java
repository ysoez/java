package dsa.list;

import dsa.array.DynamicArray;

class ArrayList<E> implements List<E> {

    private final DynamicArray<E> array;

    ArrayList(int capacity) {
        this.array = new DynamicArray<>(capacity);
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
