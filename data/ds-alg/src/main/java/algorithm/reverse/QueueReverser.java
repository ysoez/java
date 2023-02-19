package algorithm.reverse;

import util.Complexity;

import java.util.ArrayDeque;
import java.util.Queue;

class QueueReverser<E> implements Reverser<Queue<E>> {

    @Override
    @Complexity(runtime = "O(n)", space = "O(n)")
    public Queue<E> reverse(Queue<E> queue) {
        var stack = new ArrayDeque<E>();
        while (!queue.isEmpty())
            stack.push(queue.remove());
        while (!stack.isEmpty())
            queue.add(stack.pop());
        return queue;
    }

}
