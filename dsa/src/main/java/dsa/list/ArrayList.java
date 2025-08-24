package dsa.list;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;
import dsa.array.DynamicArray;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;

class ArrayList<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private final DynamicArray<E> array;

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    ArrayList(int initialCapacity) {
        this.array = new DynamicArray<>(initialCapacity);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void insertFirst(E value) {
        array.insertFirst(value);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void insertAt(int index, E value) {
        array.insertAt(index, value);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public void insertLast(E value) {
        array.insertLast(value);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void set(int index, E value) {
        array.set(index, value);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E get(int index) {
        return array.get(index);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E getFromEnd(int offset) {
        return array.getFromEnd(offset);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int size() {
        return array.size();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public int indexOf(E value) {
        return array.indexOf(value);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E deleteFirst() {
        return array.deleteFirst();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E deleteAt(int index) {
        return array.deleteAt(index);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E deleteLast() {
        return array.deleteLast();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public E[] toArray() {
        return array.toArray();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public void reverse() {
        array.reverse();
    }

}
