package adt;

public interface DataStructure<E> {

    void insert(E value);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    boolean contains(E value);

    void remove(E value);

    void clear();

}
