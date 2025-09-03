package dsa.graph.tree.heap;

interface Heap<E extends Comparable<E>> {

    void insert(E value);

    E remove();

    int size();

    boolean isFull();

    boolean isEmpty();

    E max();

}
