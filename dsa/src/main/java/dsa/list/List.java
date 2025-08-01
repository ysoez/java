package dsa.list;

public interface List<E> {

    void insertFirst(E value);

    void insertAt(int index, E value);

    void insertLast(E value);

    int size();

    boolean isEmpty();

    int indexOf(E value);

    E deleteFirst();

    E deleteAt(int index);

    E deleteLast();

}
