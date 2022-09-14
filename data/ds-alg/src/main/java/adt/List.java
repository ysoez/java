package adt;

import java.util.Comparator;

public interface List<E> extends DataStructure<E> {

    void insertAt(int index, E value);

    int indexOf(E value);

    @Override
    default boolean contains(E value) {
        return indexOf(value) != -1;
    }

    E max(Comparator<E> comparator);

    E[] intersect(E[] array, Comparator<E> comparator);

    void reverse();

    void removeAt(int index);

    @Override
    String toString();

}
