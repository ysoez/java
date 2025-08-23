package dsa.stack;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;
import dsa.array.Arrays;

import java.util.EmptyStackException;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;

class ArrayStack<E> implements Stack<E> {

    private final E[] elements;
    private int top;

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    ArrayStack(int capacity) {
        elements = Arrays.newArray(capacity);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void push(E e) {
        if (isFull())
            throw new FullStackException();
        elements[top++] = e;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E pop() {
        throwIfEmpty();
        return elements[--top];
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E peek() {
        throwIfEmpty();
        return elements[top - 1];
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return top == 0;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private boolean isFull() {
        return top == elements.length;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void throwIfEmpty() {
        if (isEmpty())
            throw new EmptyStackException();
    }

}
