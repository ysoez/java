package adt;

public interface LinkedList<E> extends List<E> {

    void insertFirst(E value);

    void removeFirst();

    void removeLast();

    @Override
    String toString();

    interface Node<E> {
        E value();

        Node<E> next();

        void setNext(Node<E> next);
    }

}
