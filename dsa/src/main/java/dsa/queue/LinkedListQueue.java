package dsa.queue;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;
import lombok.RequiredArgsConstructor;

import static dsa.Algorithm.Complexity.CONSTANT;

class LinkedListQueue<E> implements Queue<E> {

    private Node<E> front;
    private Node<E> rear;

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void enqueue(E e) {
        var node = new Node<>(e);
        if (isEmpty())
            front = rear = node;
        else {
            rear.next = node;
            rear = node;
        }
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E poll() {
        if (isEmpty())
            throw new EmptyQueueException();
        var polled = front;
        front = front.next;
        return polled.value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E peek() {
        if (isEmpty())
            throw new EmptyQueueException();
        return front.value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return front == null;
    }

    @RequiredArgsConstructor
    private static class Node<E> {
        final E value;
        Node<E> next;
    }

}
