package library.stream;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

class StreamGathererGraphExpansion {

    public static void main(String[] args) {
        var graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "E");

        Stream.of("A").gather(new BFSGatherer(graph, "A")).forEach(System.out::println);
    }

    private record BFSGatherer(Graph graph, String startNode) implements Gatherer<String, Set<String>, String> {
        @Override
        public Supplier<Set<String>> initializer() {
            return () -> {
                Set<String> visited = new HashSet<>();
                visited.add(startNode);
                return visited;
            };
        }
        @Override
        public Integrator<Set<String>, String, String> integrator() {
            return Integrator.of((state, element, downstream) -> {
                //
                // ~ emit the current node
                //
                downstream.push(element);
                //
                // ~ get neighbors and add unvisited ones to the downstream
                //
                for (String neighbor : graph.getNeighbors(element)) {
                    if (!state.contains(neighbor)) {
                        state.add(neighbor);
                        downstream.push(neighbor);
                    }
                }
                return true;
            });
        }
    }

    private static class Graph {
        private final Map<String, List<String>> adjacencyList = new HashMap<>();

        public void addNode(String node) {
            adjacencyList.computeIfAbsent(node, k -> new ArrayList<>());
        }

        public void addEdge(String node1, String node2) {
            adjacencyList.get(node1).add(node2);
            adjacencyList.get(node2).add(node1);
        }

        public List<String> getNeighbors(String node) {
            return adjacencyList.getOrDefault(node, Collections.emptyList());
        }
    }

}