package data_structure.linked_list;

import algorithm.Reverser;
import util.Complexity;

import java.util.NoSuchElementException;
import java.util.Objects;

class SinglyLinkedList<E> implements LinkedList<E> {

    private UnaryNode<E> first;
    private UnaryNode<E> last;
    private int size;

    @Complexity(runtime = "O(n)", space = "O(n)")
    SinglyLinkedList(E... values) {
        for (E value : values)
            addLast(value);
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public void addFirst(E value) {
        var node = new UnaryNode<>(value);
        if (isEmpty()) {
            first = last = node;
        } else {
            node.next = first;
            first = node;
        }
        size++;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public void addLast(E value) {
        var node = new UnaryNode<>(value);
        if (isEmpty()) {
            first = last = node;
        } else {
            last.next = node;
            last = node;
        }
        size++;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public E getFirst() {
        return first != null ? first.value : null;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public E getLast() {
        return last != null ? last.value : null;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public int size() {
        return size;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public boolean contains(E value) {
        return indexOf(value) != -1;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public int indexOf(E value) {
        var current = first;
        int index = 0;
        while (current != null) {
            if (Objects.equals(current.value, value))
                return index;
            current = current.next;
            index++;
        }
        return -1;
    }

    @Override
    @Complexity(runtime = "O(1)", space = "O(1)")
    public E removeFirst() {
        throwIfEmpty();
        UnaryNode<E> firstNode = first;
        if (first == last)
            first = last = null;
        else
            first = first.next;
        firstNode.next = null;
        size--;
        return firstNode.value;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(1)")
    public E removeLast() {
        throwIfEmpty();
        UnaryNode<E> lastNode = last;
        if (first == last) {
            first = last = null;
        } else {
            UnaryNode<E> beforeLast = getPrevious(last);
            beforeLast.next = null;
            last = beforeLast;
        }
        size--;
        return lastNode.value;
    }

    @Complexity(runtime = "O(n)", space = "O(1)")
    public void reverse() {
        if (isEmpty())
            return;
        UnaryNode<E> firstNode = Reverser.reverse(first);
        last = first;
        last.next = null;
        first = firstNode;
    }

    @Override
    @Complexity(runtime = "O(n)", space = "O(n)")
    public Object[] toArray() {
        var array = new Object[size];
        var current = first;
        var index = 0;
        while (current != null) {
            array[index++] = current.value;
            current = current.next;
        }
        return array;
    }

    @Complexity(runtime = "O(n)", space = "O(1)")
    private UnaryNode<E> getPrevious(UnaryNode<E> node) {
        if (isEmpty() || first == last)
            throw new IllegalStateException("Linked list must contain at least 2 elements");
        var current = first;
        while (current != null) {
            if (current.next == node)
                break;
            current = current.next;
        }
        return current;
    }

    @Complexity(runtime = "O(1)", space = "O(1)")
    private void throwIfEmpty() {
        if (isEmpty())
            throw new NoSuchElementException("Linked list is empty");
    }

}
