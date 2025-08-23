package dsa.stack;

import lombok.RequiredArgsConstructor;

import java.util.EmptyStackException;

class LinkedListStack<E> implements Stack<E> {

    private Node<E> top;

    @Override
    public void push(E e) {
        var node = new Node<>(e);
        if (!isEmpty())
            node.next = top;
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
