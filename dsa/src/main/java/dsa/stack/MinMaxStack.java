package dsa.stack;

interface MinMaxStack<E extends Comparable<E>> extends Stack<E> {

    E min();

    E max();

}
