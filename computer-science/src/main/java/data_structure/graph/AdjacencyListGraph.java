package data_structure.graph;

import lombok.RequiredArgsConstructor;

import java.util.*;

class AdjacencyListGraph implements Graph {

    // ArrayList is bad since we need iterate over it to check whether exist or not
    // lookup is O(N)
    private final Map<String, Node> nodes = new HashMap<>();
    // array of lists -> working with indexes
    // using map we have more oop
    private final Map<Node, List<Node>> adjacencyList = new HashMap<>();

    @Override
    public void addNode(String label) {
        var node = new Node(label);
        nodes.putIfAbsent(label, node);
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    @Override
    public void addEdge(String from, String to) {
        var fromNode = nodes.get(from);
        if (fromNode == null)
            throw new IllegalArgumentException("Not a valid node");
        var toNode = nodes.get(to);
        if (toNode == null)
            throw new IllegalArgumentException("Not a valid node");
        adjacencyList.get(fromNode).add(toNode);
    }

    @Override
    public void removeNode(String label) {
        var node = nodes.get(label);
        if (node == null)
            return;
        for (var no : adjacencyList.keySet())
            adjacencyList.get(no).remove(node);
        adjacencyList.remove(node);
        nodes.remove(node);
    }

    @Override
    public void removeEdge(String from, String to) {
        var fromNode = nodes.get(from);
        var toNode = nodes.get(to);
        if (fromNode == null || toNode == null)
            return;
        adjacencyList.get(fromNode).remove(toNode);
    }

    // DEPTH FIRST

    // recusive
    public void traverseDepthFirst(String root) {
        Node node = nodes.get(root);
        if (node == null)
            return;
        traverseDepthFirst(node, new HashSet<>());
    }

    private void traverseDepthFirst(Node root, Set<Node> visited) {
        System.out.println(root);
        visited.add(root);

        for (var node : adjacencyList.get(root))
            if (!visited.contains(node))
                traverseDepthFirst(node, visited);
    }

    // iterative (works like call stack)
    public void traverseDepthFirstIter(String root) {
        Node node = nodes.get(root);
        if (node == null)
            return;
        var visited = new HashSet<Node>();
        var stack = new Stack<Node>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (visited.contains(current))
                continue;
            System.out.println(current);
            visited.add(current);
            for (Node neighbour : adjacencyList.get(current)) {
                if (!visited.contains(neighbour))
                    stack.push(neighbour);
            }
        }
    }

    // BREADTH
    void traverseBreadthFirst(String root) {
        var node = nodes.get(root);
        if (node == null)
            return;
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node current = queue.remove();
            if (visited.contains(current))
                continue;
            System.out.println(current);
            visited.add(current);

            for (var neighbour : adjacencyList.get(current)) {
                if (!visited.contains(neighbour))
                    queue.add(neighbour);
            }
        }
    }


    // topoloigical sorting
    public List<String> topologicalSort() {
        var visited = new HashSet<Node>();
        var stack = new Stack<Node>();
        for (var node : nodes.values())
            topologicalSort(node, visited, stack);
        List<String> sorted = new ArrayList<>();
        while (!stack.isEmpty()) {
            sorted.add(stack.pop().label);
        }
        return sorted;
    }

    private void topologicalSort(Node node, Set<Node> visited, Stack<Node> stack) {
        if (visited.contains(node))
            return;
        visited.add(node);
        for (Node neighbour : adjacencyList.get(node))
            topologicalSort(neighbour, visited, stack);
        stack.push(node);
    }

    // cycle detection
    boolean hasCycle() {
        var all = new HashSet<>(nodes.values());
        var visiting = new HashSet<Node>();
        var visited = new HashSet<Node>();

        while (!all.isEmpty()) {
            Node current = all.iterator().next();
            if (hasCycle(current, all, visiting, visited))
                return true;
        }
        return false;
    }

    private boolean hasCycle(Node node, Set<Node> all, Set<Node> visiting, Set<Node> visited) {
        all.remove(node);
        visiting.add(node);

        for (var neighbour : adjacencyList.get(node)) {
            if (visited.contains(neighbour))
                continue;
            if (visiting.contains(neighbour))
                return true;
            if (hasCycle(neighbour, all, visiting, visited))
                return true;
        }

        visiting.remove(node);
        visited.add(node);

        return false;
    }



    public void print() {
        for (Node source : adjacencyList.keySet()) {
            List<Node> targets = adjacencyList.get(source);
            if (!targets.isEmpty()) {
                System.out.println(source + " is connected to " + targets);
            }
        }
    }

    @RequiredArgsConstructor
    private static class Node {
        private final String label;

        @Override
        public String toString() {
            return label;
        }
    }

}
