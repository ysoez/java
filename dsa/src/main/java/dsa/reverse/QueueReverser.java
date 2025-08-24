package dsa.reverse;

import java.util.ArrayDeque;
import java.util.Queue;

class QueueReverser<T> implements Reverser<Queue<T>> {

    @Override
    public Queue<T> apply(Queue<T> queue) {
        if (queue == null)
            throw new IllegalArgumentException();
        if (queue.isEmpty())
            return queue;
        var stack = new ArrayDeque<T>();
        while (!queue.isEmpty())
            stack.push(queue.poll());
        while (!stack.isEmpty())
            queue.offer(stack.pop());
        return queue;
    }

}
