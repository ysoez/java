package dsa.queue;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.ArrayDeque;
import java.util.Deque;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;

class StackQueue<E> implements Queue<E> {

    private final Deque<E> enqueueStack = new ArrayDeque<>();
    private final Deque<E> dequeueStack = new ArrayDeque<>();

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void enqueue(E e) {
        enqueueStack.push(e);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E poll() {
        if (isEmpty())
            throw new EmptyQueueException();
        flipToDequeueStack();
        return dequeueStack.pop();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E peek() {
        if (isEmpty())
            throw new EmptyQueueException();
        flipToDequeueStack();
        return dequeueStack.peek();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return enqueueStack.isEmpty() && dequeueStack.isEmpty();
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    private void flipToDequeueStack() {
        if (dequeueStack.isEmpty())
            while (!enqueueStack.isEmpty())
                dequeueStack.push(enqueueStack.pop());
    }

}
