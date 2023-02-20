package data_structure.stack;

import util.Complexity;

import java.util.Arrays;
import java.util.EmptyStackException;

import static java.lang.System.arraycopy;

class ArrayStack<E> implements Stack<E> {

    private Object[] elements;
    private int top;

    @Complexity(runtime = "O(1)", space = "O(1)")
    ArrayStack() {
        elements = new Object[10];
    }

    @Complexity(runtime = "O(n)", space = "O(n)")
    ArrayStack(E... values) {
        elements = new Object[values.length];
        top = values.length;
        arraycopy(values, 0, elements, 0, values.length);
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public void push(E value) {
        if (isFull())
            throw new StackOverflowError();
        elements[top++] = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Complexity(runtime = "O(1)", space = "O(1)")
    public E peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return (E) elements[top - 1];
    }

    @Override
    @SuppressWarnings("unchecked")
    @Complexity(runtime = "O(1)", space = "O(1)")
    public E pop() {
        if (isEmpty())
            throw new EmptyStackException();
        return (E) elements[--top];
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(n)")
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(elements, 0, top));
    }

    @Complexity(runtime = "O(1)", space = "O(1)")
    private boolean isFull() {
        return top == elements.length;
    }

}
