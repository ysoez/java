package dsa.array;

interface ResizableArray<E> extends Array<E> {

    void insertFirst(E value);

    void insertAt(int index, E value);

    void insertLast(E value);

    int size();

    boolean isEmpty();

    int indexOf(E value);

    void trimToSize();

    E deleteFirst();

    E deleteAt(int index);

    E deleteLast();

}
