package dsa.graph;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testTopologicalSort() {
        var graph = newGraph();
        graph.addNode("core-lib");
        graph.addNode("compression-lib");
        graph.addNode("encryption-lib");
        graph.addNode("project");

        graph.addEdge("core-lib", "compression-lib");
        graph.addEdge("core-lib", "encryption-lib");
        graph.addEdge("compression-lib", "project");
        graph.addEdge("encryption-lib", "project");

        assertEquals(List.of("core-lib", "encryption-lib", "compression-lib", "project"), graph.topologicalSort());
    }

    @Test
    void testCyclicGraph() {
        var graph = newGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        assertTrue(graph.hasCycle());
    }

    @Test
    void testAcyclicGraph() {
        var graph = newGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "C");

        assertFalse(graph.hasCycle());
    }

}