package dsa.graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class GraphTest {

    abstract Graph<String> newGraph();

    @Test
    void testOperations() {
        var graph = newGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        assertEquals(Set.of("B", "C"), graph.neighboursOf("A"));

        graph.removeEdge("A", "C");
        assertEquals(Set.of("B"), graph.neighboursOf("A"));

        graph.removeEdge("A", "B");
        assertEquals(Set.of(), graph.neighboursOf("A"));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testDft(boolean isRecursive) {
        var graph = newGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");

        graph.addEdge("A", "B");
        graph.addEdge("B", "D");
        graph.addEdge("D", "C");
        graph.addEdge("A", "C");

        var out = graph.dft(isRecursive).startFrom("A");
        assertEquals(List.of("A", "B", "D", "C"), out);

        out = graph.dft(isRecursive).startFrom("C");
        assertEquals(List.of("C"), out);

        out = graph.dft(isRecursive).startFrom("G");
        assertEquals(emptyList(), out);
    }

    @Test
    void testBft() {
        var graph = newGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");

        graph.addEdge("A", "B");
        graph.addEdge("B", "D");
        graph.addEdge("D", "C");
        graph.addEdge("A", "C");

        var out = graph.bft().startFrom("A");
        assertEquals(List.of("A", "B", "C", "D"), out);

        out = graph.bft().startFrom("C");
        assertEquals(List.of("C"), out);

        out = graph.bft().startFrom("G");
        assertEquals(List.of(), out);
    }

}