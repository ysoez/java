package adt;

public interface LinkedList<E> extends List<E> {

    void insertFirst(E value);

    interface Node<E> {
        E value();

        Node<E> next();

        void setNext(Node<E> next);
    }

}
