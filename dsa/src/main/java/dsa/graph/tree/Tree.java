package dsa.graph.tree;

import java.util.Collection;

interface Tree<E extends Comparable<E>> {

    void insert(E value);

    boolean find(E value);

    Collection<E> getAtDistance(int distance);

    Collection<E> getAncestors(E value);

    boolean isEmpty();

    boolean isBst();

    boolean contains(E value);

    boolean areSiblings(E first, E second);

    int size();

    int height();

    E min();

    E max();

    int leavesCount();

//     void remove(E value);

}
