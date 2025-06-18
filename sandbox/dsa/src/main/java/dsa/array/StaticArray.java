package dsa.array;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Complexity.Value.CONSTANT;
import static dsa.Algorithm.Complexity.Value.LINEAR;
import static dsa.array.Arrays.*;

class StaticArray<E> implements Array<E> {

    private static final int DEFAULT_CAPACITY = 10;
    protected E[] elements;

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    StaticArray() {
        elements = newArray(DEFAULT_CAPACITY);
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = LINEAR))
    StaticArray(int capacity) {
        elements = newArray(capacity);
    }

    @SafeVarargs
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    StaticArray(E... values) {
        elements = newArray(values.length);
        System.arraycopy(values, 0, elements, 0, values.length);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void set(int index, E value) {
        checkIndex(index, elements.length);
        elements[index] = value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E get(int index) {
        checkIndex(index, elements.length);
        return elements[index];
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int length() {
        return elements.length;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public String toString() {
        if (elements.length == 0) {
            return "[]";
        }
        var builder = new StringBuilder("[");
        for (int i = 0; i < elements.length; i++) {
            builder.append(elements[i]);
            if (i == elements.length - 1) {
                builder.append("]");
            } else {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
