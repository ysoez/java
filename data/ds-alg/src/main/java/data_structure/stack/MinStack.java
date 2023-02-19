package data_structure.stack;

import lombok.RequiredArgsConstructor;
import util.Complexity;

import java.util.EmptyStackException;

class MinStack<E extends Comparable<E>> implements Stack<E> {

    private StackNode<E> top;

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
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
    @Complexity(runtime = "O(1)", space = "O(1)")
    public E peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return top.value;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public E pop() {
        if (isEmpty())
            throw new EmptyStackException();
        E poppedValue = top.value;
        top = top.prev;
        return poppedValue;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public boolean isEmpty() {
        return top == null;
    }

    @Complexity(runtime = "O(1)", space = "O(1)")
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
