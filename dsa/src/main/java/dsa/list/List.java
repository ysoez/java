package dsa.list;

public interface List<E> {

    void insertFirst(E value);

    void insertAt(int index, E value);

    void insertLast(E value);

    void set(int index, E value);

    E get(int index);

    int size();

    boolean isEmpty();

    int indexOf(E value);

    default boolean contains(E value) {
        return indexOf(value) >= 0;
    }

    E deleteFirst();

    E deleteAt(int index);

    E deleteLast();

}
