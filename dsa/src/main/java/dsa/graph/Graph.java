package dsa.graph;

import java.util.List;
import java.util.Set;

interface Graph<E> {

    void addNode(E e);

    void addEdge(E from, E to);

    void removeNode(E e);

    void removeEdge(E from, E to);

    Set<E> neighboursOf(E e);

    Traverser<E> dft(boolean recursive);

    Traverser<E> bft();

    List<E> topologicalSort();

    interface Traverser<E> {
        List<E> startFrom(E val);
    }

}
