package dsa.stack;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;
import lombok.RequiredArgsConstructor;

import java.util.EmptyStackException;

import static dsa.Algorithm.Complexity.CONSTANT;

class LinkedListStack<E> implements Stack<E> {

    private Node<E> top;

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void push(E e) {
        var node = new Node<>(e);
        if (!isEmpty())
            node.next = top;
        top = node;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E pop() {
        throwIfEmpty();
        var value = top.value;
        top = top.next;
        return value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E peek() {
        throwIfEmpty();
        return top.value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return top == null;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void throwIfEmpty() {
        if (isEmpty())
            throw new EmptyStackException();
    }

    @RequiredArgsConstructor
    private static class Node<E> {
        private final E value;
        private Node<E> next;
    }

}
