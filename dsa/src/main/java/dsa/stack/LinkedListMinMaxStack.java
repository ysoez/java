package dsa.stack;

import lombok.RequiredArgsConstructor;

import java.util.EmptyStackException;

class LinkedListMinMaxStack<E extends Comparable<E>> implements MinMaxStack<E> {

    private Node<E> top;

    @Override
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
    public E pop() {
        throwIfEmpty();
        var value = top.value;
        top = top.next;
        return value;
    }

    @Override
    public E peek() {
        throwIfEmpty();
        return top.value;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public E min() {
        throwIfEmpty();
        return top.min;
    }

    @Override
    public E max() {
        throwIfEmpty();
        return top.max;
    }

    private E min(E e1, E e2) {
        if (e1 == null)
            return e2;
        if (e2 == null)
            return e1;
        return e1.compareTo(e2) <= 0 ? e1 : e2;
    }

    private E max(E e1, E e2) {
        if (e1 == null)
            return e2;
        if (e2 == null)
            return e1;
        return e1.compareTo(e2) >= 0 ? e1 : e2;
    }

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
