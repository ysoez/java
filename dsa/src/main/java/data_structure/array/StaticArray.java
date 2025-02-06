package data_structure.array;

import data_structure.Algorithm;
import data_structure.Algorithm.Complexity;

import static data_structure.Algorithm.Complexity.Value.CONSTANT;
import static data_structure.Algorithm.Complexity.Value.LINEAR;
import static data_structure.array.Arrays.checkCapacity;
import static data_structure.array.Arrays.checkIndex;

class StaticArray<E> implements Array<E> {

    Object[] elements;

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = LINEAR))
    StaticArray(int capacity) {
        checkCapacity(capacity);
        elements = new Object[capacity];
    }

    @SafeVarargs
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    StaticArray(E... values) {
        elements = new Object[values.length];
        System.arraycopy(values, 0, elements, 0, values.length);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void set(int index, E value) {
        checkIndex(index, elements.length);
        elements[index] = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E get(int index) {
        checkIndex(index, elements.length);
        return (E) elements[index];
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int length() {
        return elements.length;
    }

}
