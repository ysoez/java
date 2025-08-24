package dsa.queue;

class ArrayBoundedPriorityQueue<E extends Comparable<E>> implements BoundedPriorityQueue<E> {

    private final E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    ArrayBoundedPriorityQueue(int maxSize) {
        //java.lang.ClassCastException: class [Ljava.lang.Object; cannot be cast to class [Ljava.lang.Comparable;
//        elements = Arrays.newArray(maxSize);
        elements = (E[]) java.lang.reflect.Array.newInstance(Comparable.class, maxSize);
    }

    @Override
    public void enqueue(E e) {
        if (isFull())
            throw new FullQueueException();
        int i = shiftElementsToInsert(e);
        elements[i] = e;
        size++;
    }

    // larger first (use size pointer)
    @Override
    public E poll() {
        if (isEmpty())
            throw new EmptyQueueException();
        return elements[--size];
    }

    @Override
    public E peek() {
        if (isEmpty())
            throw new EmptyQueueException();
        return elements[size - 1];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isFull() {
        return size == elements.length;
    }

    private int shiftElementsToInsert(E e) {
        int i;
        for (i = size - 1; i >= 0; i--)
            if (elements[i].compareTo(e) > 0)
                elements[i + 1] = elements[i];
            else
                break;
        return i + 1;
    }

}
