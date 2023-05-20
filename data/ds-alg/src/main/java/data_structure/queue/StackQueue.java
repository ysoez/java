package data_structure.queue;

import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.ArrayDeque;
import java.util.Deque;

import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;

class StackQueue<E> implements Queue<E> {

    private final Deque<E> enqueueStack = new ArrayDeque<>();
    private final Deque<E> dequeueStack = new ArrayDeque<>();

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void enqueue(E value) {
        enqueueStack.push(value);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E dequeue() {
        if (isEmpty())
            throw new IllegalStateException("Queue is empty");
        moveElementsBetweenStacks();
        return dequeueStack.pop();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E peek() {
        if (isEmpty())
            throw new IllegalStateException("Queue is empty");
        moveElementsBetweenStacks();
        return dequeueStack.peek();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return enqueueStack.isEmpty() && dequeueStack.isEmpty();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isFull() {
        return false;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    private void moveElementsBetweenStacks() {
        if (dequeueStack.isEmpty())
            while (!enqueueStack.isEmpty())
                dequeueStack.push(enqueueStack.pop());
    }

}
