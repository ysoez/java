package dsa.reverse;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.ArrayDeque;
import java.util.Collection;

import static dsa.Algorithm.Complexity.LINEAR;

class CollectionReverser<E> implements Reverser<Collection<E>> {

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public Collection<E> apply(Collection<E> collection) {
        if (collection == null)
            throw new IllegalArgumentException();
        if (collection.size() < 2)
            return collection;
        var stack = new ArrayDeque<E>();
        for (E e : collection)
            stack.push(e);
        collection.clear();
        while (!stack.isEmpty())
            collection.add(stack.pop());
        return collection;
    }

}
