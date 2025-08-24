package dsa.queue;

interface Queue<E> {

    void enqueue(E e);

    E poll();

    E peek();

}
