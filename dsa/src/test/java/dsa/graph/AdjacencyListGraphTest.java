package dsa.graph;

class AdjacencyListGraphTest extends GraphTest {

    @Override
    Graph<String> newGraph() {
        return new AdjacencyListGraph<>();
    }

}