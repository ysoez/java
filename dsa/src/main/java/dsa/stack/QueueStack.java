package dsa.stack;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.Queue;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;

class QueueStack<E> implements Stack<E> {

    private Queue<E> pushQueue = new ArrayDeque<>();
    private Queue<E> queue2 = new ArrayDeque<>();
    private E top;

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void push(E e) {
        pushQueue.add(e);
        top = e;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E pop() {
        if (isEmpty())
            throw new EmptyStackException();
        while (pushQueue.size() > 1) {
            //
            // ~ move all the items except the last one
            //
            top = pushQueue.remove();
            queue2.add(top);
        }
        swapQueues();
        return queue2.remove();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E peek() {
        if (isEmpty())
            throw new EmptyStackException();
        return top;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return pushQueue.isEmpty();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public String toString() {
        return pushQueue.toString();
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void swapQueues() {
        var temp = pushQueue;
        pushQueue = queue2;
        queue2 = temp;
    }

}
