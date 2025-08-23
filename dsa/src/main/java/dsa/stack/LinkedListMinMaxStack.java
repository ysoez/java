package dsa.stack;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;
import lombok.RequiredArgsConstructor;

import java.util.EmptyStackException;

import static dsa.Algorithm.Complexity.CONSTANT;

class LinkedListMinMaxStack<E extends Comparable<E>> implements MinMaxStack<E> {

    private Node<E> top;

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void push(E e) {
        var node = new Node<>(e);
        if (isEmpty()) {
            node.min = e;
            node.max = e;
        } else {
            node.next = top;
            node.min = min(e, top.min);
            node.max = max(e, top.max);
        }
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

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E min() {
        throwIfEmpty();
        return top.min;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E max() {
        throwIfEmpty();
        return top.max;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private E min(E e1, E e2) {
        if (e1 == null)
            return e2;
        if (e2 == null)
            return e1;
        return e1.compareTo(e2) <= 0 ? e1 : e2;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private E max(E e1, E e2) {
        if (e1 == null)
            return e2;
        if (e2 == null)
            return e1;
        return e1.compareTo(e2) >= 0 ? e1 : e2;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void throwIfEmpty() {
        if (isEmpty())
            throw new EmptyStackException();
    }

    @RequiredArgsConstructor
    private static class Node<E> {
        private final E value;
        private E min;
        private E max;
        private Node<E> next;
    }

}
