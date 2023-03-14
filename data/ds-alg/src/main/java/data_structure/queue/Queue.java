package data_structure.queue;

interface Queue<E> {

    void enqueue(E value);

    E dequeue();

    E peek();

    boolean isEmpty();

    boolean isFull();

}
