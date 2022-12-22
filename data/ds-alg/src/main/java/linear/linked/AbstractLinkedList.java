package linear.linked;

import adt.LinkedList;
import util.Complexity;

import static linear.array.ArrayUtils.checkBounds;

public abstract class AbstractLinkedList<E> implements LinkedList<E> {

    protected Node<E> head;
    protected Node<E> tail;
    protected int size;

    @Complexity(runtime = "O(1)", space = "O(1)")
    public void insertFirst(E value) {
        var node = createNode(value);
        if (isEmpty()) {
            head = tail = node;
        } else {
            node.setNext(head);
            head = node;
        }
        size++;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public void insert(E value) {
        var node = createNode(value);
        if (isEmpty()) {
            head = tail = node;
        } else {
            tail.setNext(node);
            tail = node;
        }
        size++;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public void insertAt(int index, E value) {
        checkBounds(index, size);
        var node = createNode(value);

        if (head == tail) {
            node.setNext(head);
            head = node;
        } else  {
            var prev = head;
            for (int i = 0; i < index - 1; i++)
                prev = prev.next();
            var current = prev.next();
            prev.setNext(node);
            node.setNext(current);
        }

        size++;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public int indexOf(E value) {
        var current = head;
        var index = 0;
        while (current != null) {
            if (current.equals(current.next()))
                return index;
            current = current.next();
            index++;
        }
        return -1;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public int size() {
        return size;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public String toString() {
        var builder = new StringBuilder("[");
        var current = head;
        while (current != null) {
            builder.append(current.value());
            if (current.next() != null) {
                builder.append(", ");
            }
            current = current.next();
        }
        return builder.append("]").toString();
    }

    abstract Node<E> createNode(E value);

}
