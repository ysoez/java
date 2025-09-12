package dsa.graph;

import lombok.AllArgsConstructor;

import java.util.*;

class UndirectedGraph<E> implements WeightedGraph<E> {

    private final Map<E, Node<E>> nodes = new HashMap<>();

    @Override
    public void addNode(E val) {
        nodes.putIfAbsent(val, new Node<>(val));
    }

    @Override
    public void addEdge(E from, E to) {
        addEdge(from, to, 0);
    }

    @Override
    public void addEdge(E from, E to, int weight) {
        var fromNode = nodes.get(from);
        if (fromNode == null)
            throw new IllegalArgumentException();
        var toNode = nodes.get(to);
        if (toNode == null)
            throw new IllegalArgumentException();
        //
        // ~ undirected
        //
        fromNode.addEdge(toNode, weight);
        toNode.addEdge(fromNode, weight);
    }

    @Override
    public void removeNode(E e) {

    }

    @Override
    public void removeEdge(E from, E to) {

    }

    @Override
    public Set<E> neighboursOf(E e) {
        return Set.of();
    }

    @Override
    public Traverser<E> dft(boolean recursive) {
        return null;
    }

    @Override
    public Traverser<E> bft() {
        return null;
    }

    @Override
    public List<E> topologicalSort() {
        return List.of();
    }

    @Override
    public boolean hasCycle() {
        return false;
    }

    private static class Node<E> {
        private final E value;
        private final List<Edge<E>> edges;

        Node(E value) {
            this.value = value;
            this.edges = new ArrayList<>();
        }

        void addEdge(Node<E> to, int weight) {
            edges.add(new Edge<>(this, to, weight));
        }
    }

    @AllArgsConstructor
    private static class Edge<E> {
        private Node<E> from;
        private Node<E> to;
        private int weight;
    }

}
