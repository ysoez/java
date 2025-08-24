package dsa.queue;

interface BoundedPriorityQueue<E extends Comparable<E>> extends PriorityQueue<E>, BoundedQueue<E> {
}
