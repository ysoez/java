package dsa.graph;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toUnmodifiableSet;

class AdjacencyListGraph<E> implements Graph<E> {

    private final Map<E, Node<E>> nodes = new HashMap<>();
    private final Map<Node<E>, List<Node<E>>> adjacencyList = new HashMap<>();

    @Override
    public void addNode(E e) {
        var node = new Node<>(e);
        nodes.putIfAbsent(e, node);
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    @Override
    public void addEdge(E from, E to) {
        var fromNode = nodes.get(from);
        throwIfNull(fromNode);
        var toNode = nodes.get(to);
        throwIfNull(toNode);
        //
        // ~ unidirected graph
        //
        adjacencyList.get(fromNode).add(toNode);
    }

    @Override
    public void removeNode(E e) {
        var node = nodes.get(e);
        //
        // ~ already deleted
        //
        if (node == null)
            return;
        for (Node<E> n : adjacencyList.keySet())
            adjacencyList.get(n).remove(node);
        adjacencyList.remove(node);
        nodes.remove(e);
    }

    @Override
    public void removeEdge(E from, E to) {
        var fromNode = nodes.get(from);
        var toNode = nodes.get(to);
        //
        // ~ already deleted
        //
        if (from == null || to == null)
            return;
        adjacencyList.get(fromNode).remove(toNode);
    }

    @Override
    public Set<E> neighboursOf(E e) {
        var node = nodes.get(e);
        if (node == null)
            return emptySet();
        return adjacencyList.get(node).stream().map(Node::value).collect(toUnmodifiableSet());
    }

    @Override
    public Traverser<E> dft(boolean recursive) {
        return recursive ? new DftRecursive() : new DftIterative();
    }

    @Override
    public Traverser<E> bft() {
        return new Bft();
    }

    @Override
    public List<E> topologicalSort() {
        var stack = new ArrayDeque<Node<E>>();
        var visited = new HashSet<Node<E>>();
        for (var node : nodes.values())
            topologicalSort(node, visited, stack);
        var sorted = new ArrayList<E>();
        while (!stack.isEmpty())
            sorted.add(stack.pop().value);
        return sorted;
    }

    private void topologicalSort(Node<E> node, Set<Node<E>> visited, Deque<Node<E>> stack) {
        if (visited.contains(node))
            return;
        visited.add(node);
        for (Node<E> neighbour : adjacencyList.get(node))
            topologicalSort(neighbour, visited, stack);
        stack.push(node);
    }

    private void throwIfNull(Node<E> node) {
        if (node == null)
            throw new IllegalArgumentException();
    }

    private record Node<E>(E value) {}

    private class DftRecursive implements Graph.Traverser<E> {
        @Override
        public List<E> startFrom(E val) {
            var node = nodes.get(val);
            if (node == null)
                return emptyList();
            var visited = new LinkedHashSet<Node<E>>();
            traverse(node, visited);
            return visited.stream().map(Node::value).toList();
        }

        private void traverse(Node<E> node, Set<Node<E>> visited) {
            visited.add(node);
            for (Node<E> neighbour : adjacencyList.get(node))
                if (!visited.contains(neighbour))
                    traverse(neighbour, visited);
        }
    }

    private class DftIterative implements Graph.Traverser<E> {
        @Override
        public List<E> startFrom(E val) {
            var node = nodes.get(val);
            if (node == null)
                return emptyList();
            var visited = new LinkedHashSet<Node<E>>();
            var stack = new ArrayDeque<Node<E>>();
            stack.push(node);
            while (!stack.isEmpty()) {
                var current = stack.pop();
                if (visited.contains(current))
                    continue;
                visited.add(current);
                //
                // ~ reverse the order of neighbors when pushing them onto the stack
                // ~ ensure the first neighbor is visited first
                //
                var neighbours = adjacencyList.get(current);
                for (int i = neighbours.size() - 1; i >= 0; i--) {
                    var neighbour = neighbours.get(i);
                    if (!visited.contains(neighbour))
                        stack.push(neighbour);
                }
            }
            return visited.stream().map(Node::value).toList();
        }
    }

    private class Bft implements Graph.Traverser<E> {
        @Override
        public List<E> startFrom(E val) {
            var node = nodes.get(val);
            if (node == null)
                return emptyList();
            var visited = new LinkedHashSet<Node<E>>();
            var queue = new ArrayDeque<Node<E>>();
            queue.offer(node);
            while (!queue.isEmpty()) {
                var current = queue.poll();
                if (visited.contains(current))
                    continue;
                visited.add(current);
                for (Node<E> neighbour : adjacencyList.get(current))
                    if (!visited.contains(neighbour))
                        queue.offer(neighbour);
            }
            return visited.stream().map(Node::value).toList();
        }
    }

}
