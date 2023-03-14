package data_structure.queue;

import util.Complexity;

import java.util.ArrayDeque;
import java.util.Deque;

class StackQueue<E> implements Queue<E> {

    private final Deque<E> enqueueStack = new ArrayDeque<>();
    private final Deque<E> dequeueStack = new ArrayDeque<>();

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public void enqueue(E value) {
        enqueueStack.push(value);
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public E dequeue() {
        if (isEmpty())
            throw new IllegalStateException("Queue is empty");
        moveElementsBetweenStacks();
        return dequeueStack.pop();
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public E peek() {
        if (isEmpty())
            throw new IllegalStateException("Queue is empty");
        moveElementsBetweenStacks();
        return dequeueStack.peek();
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public boolean isEmpty() {
        return enqueueStack.isEmpty() && dequeueStack.isEmpty();
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public boolean isFull() {
        return false;
    }

    @Complexity(runtime = "O(n)", space = "O(1)")
    private void moveElementsBetweenStacks() {
        if (dequeueStack.isEmpty())
            while (!enqueueStack.isEmpty())
                dequeueStack.push(enqueueStack.pop());
    }

}
