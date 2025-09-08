package dsa.graph;

class DirectedGraphTest extends GraphTest {

    @Override
    Graph<String> newGraph() {
        return new DirectedGraph<>();
    }

}