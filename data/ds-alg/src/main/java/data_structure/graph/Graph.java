package data_structure.graph;

interface Graph {

    void addNode(String label);

    void addEdge(String from, String to);

    void removeNode(String label);

    void removeEdge(String from, String to);

}
