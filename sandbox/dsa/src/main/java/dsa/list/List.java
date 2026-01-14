package dsa.list;

import java.util.Collection;

public interface List<E> {

    void insertFirst(E value);

    void insertAt(int index, E value);

    void insertLast(E value);

    void set(int index, E value);

    E get(int index);

    default E getFirst() {
        return get(0);
    }

    default E getLast() {
        return get(size() - 1);
    }

    E getFromEnd(int offset);

    Collection<E> getMiddle();

    int size();

    boolean isEmpty();

    int indexOf(E value);

    default boolean contains(E value) {
        return indexOf(value) != -1;
    }

    E deleteFirst();

    E deleteAt(int index);

    E deleteLast();

    void clear();

    E[] toArray();

}
