package data_structure.stack;

import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.Arrays;
import java.util.EmptyStackException;

import static java.lang.System.arraycopy;
import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;

class ArrayStack<E> implements Stack<E> {

    private Object[] elements;
    private int top;

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    ArrayStack() {
        elements = new Object[10];
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    ArrayStack(E... values) {
        elements = new Object[values.length];
        top = values.length;
        arraycopy(values, 0, elements, 0, values.length);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void push(E value) {
        if (isFull())
            throw new StackOverflowError();
        elements[top++] = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return (E) elements[top - 1];
    }

    @Override
    @SuppressWarnings("unchecked")
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E pop() {
        if (isEmpty())
            throw new EmptyStackException();
        return (E) elements[--top];
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return top == 0;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(elements, 0, top));
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private boolean isFull() {
        return top == elements.length;
    }

}
