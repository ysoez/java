package dsa.reverse;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.ArrayList;
import java.util.Stack;

import static dsa.Algorithm.Complexity.LINEAR;

class IterableReverser<T> implements Reverser<Iterable<T>> {

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public Iterable<T> apply(Iterable<T> iterable) {
        if (iterable == null)
            throw new IllegalArgumentException();
        if (!iterable.iterator().hasNext()) {
            return iterable;
        }
        var stack = new Stack<T>();
        for (T t : iterable)
            stack.push(t);
        var reversed = new ArrayList<T>();
        while (!stack.isEmpty())
            reversed.add(stack.pop());
        return reversed;
    }

}
