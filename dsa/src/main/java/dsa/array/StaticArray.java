package dsa.array;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;

class StaticArray<E> implements Array<E> {

    static final int DEFAULT_CAPACITY = 10;
    private final E[] elements;

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    StaticArray() {
        this(DEFAULT_CAPACITY);
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = LINEAR))
    StaticArray(int capacity) {
        this.elements = Arrays.newArray(capacity);
    }

    @SafeVarargs
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    StaticArray(E... values) {
        elements = Arrays.newArray(values.length);
        System.arraycopy(values, 0, elements, 0, values.length);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void set(int index, E value) {
        elements[index] = value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E get(int index) {
        return elements[index];
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int length() {
        return elements.length;
    }

}
