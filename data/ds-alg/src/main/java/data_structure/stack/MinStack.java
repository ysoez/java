package data_structure.stack;

import lombok.RequiredArgsConstructor;
import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.EmptyStackException;

import static util.Algorithm.Complexity.Value.CONSTANT;

class MinStack<E extends Comparable<E>> implements Stack<E> {

    private StackNode<E> top;

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void push(E value) {
        if (isEmpty()) {
            top = new StackNode<>(value, value);
        } else {
            E min = top.min;
            if (value != null && value.compareTo(min) < 0) {
                min = value;
            }
            var node = new StackNode<>(min, value);
            node.prev = top;
            top = node;
        }
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return top.value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E pop() {
        if (isEmpty())
            throw new EmptyStackException();
        E poppedValue = top.value;
        top = top.prev;
        return poppedValue;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return top == null;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E min() {
        if (isEmpty())
            throw new EmptyStackException();
        return top.min;
    }

    @RequiredArgsConstructor
    private static class StackNode<E> {
        private StackNode<E> prev;
        private final E min;
        private final E value;
    }

}
