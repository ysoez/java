package dsa.graph;

interface WeightedGraph<E> extends Graph<E> {

    void addEdge(E from, E to, int weight);

}
