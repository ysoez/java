package dsa.tree.heap;

interface Heap {

    void insert(int value);

    int remove();

    int size();

    boolean isFull();

    boolean isEmpty();

    int max();

}
