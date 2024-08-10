package data_structure.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListGraphTest {

    @Test
    void dsadsa() {
        var graph = new AdjacencyListGraph();

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");

        graph.print();
    }

    @Test
    void dsadsa1() {
        var graph = new AdjacencyListGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");

        graph.removeEdge("A", "C");
        graph.print();

    }

    @Test
    void removeNotExistedEdge() {
        var graph = new AdjacencyListGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");

        graph.removeEdge("A", "D");
        graph.print();
    }

    @Test
    void removeNodeIfEdgeExist() {
        var graph = new AdjacencyListGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");

        graph.removeNode("B");
        graph.print();
    }

    @Test
    void removeN() {
        var graph = new AdjacencyListGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");

        graph.removeNode("A");
        graph.addEdge("B", "C");
        graph.print();
    }

    @Test
    void dsadsadsa() {
        var graph = new AdjacencyListGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge("A", "B");
        graph.addEdge("B", "D");
        graph.addEdge("D", "C");
        graph.addEdge("A", "C");

        graph.traverseDepthFirst("C");

        graph.traverseDepthFirst("G");
    }

    @Test
    void dsadsadsa123231321() {
        var graph = new AdjacencyListGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge("A", "B");
        graph.addEdge("B", "D");
        graph.addEdge("D", "C");
        graph.addEdge("A", "C");

//        graph.traverseDepthFirstIter("A");
//        graph.traverseDepthFirstIter("C");
        graph.traverseDepthFirstIter("R");

    }

    @Test
    void breadFirst() {
        var graph = new AdjacencyListGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge("A", "B");
        graph.addEdge("B", "D");
        graph.addEdge("D", "C");
        graph.addEdge("A", "C");

//        graph.traverseBreadthFirst("A");
//        graph.traverseBreadthFirst("C");
        graph.traverseBreadthFirst("K");

    }

    @Test
    void topsorting() {
        var graph = new AdjacencyListGraph();
        graph.addNode("X");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("P");
        graph.addEdge("X", "A");
        graph.addEdge("X", "B");
        graph.addEdge("A", "P");
        graph.addEdge("B", "P");

        System.out.println(graph.topologicalSort());
    }

    @Test
    void cycleFalse() {
        var graph = new AdjacencyListGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "C");
        assertFalse(graph.hasCycle());
    }

    @Test
    void cycleTrue() {
        var graph = new AdjacencyListGraph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        assertTrue(graph.hasCycle());
    }

}