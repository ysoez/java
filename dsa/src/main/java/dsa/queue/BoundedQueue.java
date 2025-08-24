package dsa.queue;

interface BoundedQueue<E> extends Queue<E> {

    int size();

    boolean isFull();

}
