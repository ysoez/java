package data_structure.tree.heap;

interface Heap<E> {

    boolean isEmpty();

    void insert(E value);

    E remove();

}
