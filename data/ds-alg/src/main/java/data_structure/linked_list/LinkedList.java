package data_structure.linked_list;

interface LinkedList<E> {

    void addFirst(E value);

    void addLast(E value);

    E getFirst();

    E getLast();

    boolean isEmpty();

    int size();

    boolean contains(E value);

    int indexOf(E value);

    E removeFirst();

    E removeLast();

    Object[] toArray();

}
