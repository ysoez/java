package data_structure.array;

import util.Complexity;

import static data_structure.array.Arrays.checkCapacity;
import static data_structure.array.Arrays.checkIndex;

class StaticArray<E> implements Array<E> {

    Object[] elements;

    @Complexity(runtime = "O(1)", space = "O(n)")
    StaticArray(int capacity) {
        checkCapacity(capacity);
        elements = new Object[capacity];
    }

    @SafeVarargs
    @Complexity(runtime = "O(n)", space = "O(n)")
    StaticArray(E... values) {
        elements = new Object[values.length];
        System.arraycopy(values, 0, elements, 0, values.length);
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public void set(int index, E value) {
        checkIndex(index, elements.length);
        elements[index] = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Complexity(runtime = "O(1)", space = "O(1)")
    public E get(int index) {
        checkIndex(index, elements.length);
        return (E) elements[index];
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public int length() {
        return elements.length;
    }

}
