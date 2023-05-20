package data_structure.linked_list;

import algorithm.Reverse;
import util.Algorithm;
import util.Algorithm.Complexity;

import java.util.NoSuchElementException;
import java.util.Objects;

import static util.Algorithm.Complexity.Value.CONSTANT;
import static util.Algorithm.Complexity.Value.LINEAR;

class SinglyLinkedList<E> implements LinkedList<E> {

    private UnaryNode<E> first;
    private UnaryNode<E> last;
    private int size;

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    SinglyLinkedList(E... values) {
        for (E value : values)
            addLast(value);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
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
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
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
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E getFirst() {
        return first != null ? first.value : null;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E getLast() {
        return last != null ? last.value : null;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int size() {
        return size;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public boolean contains(E value) {
        return indexOf(value) != -1;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
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
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
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
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
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

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public void reverse() {
        if (isEmpty())
            return;
        UnaryNode<E> firstNode = Reverse.linkedList(first);
        last = first;
        last.next = null;
        first = firstNode;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
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

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
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

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void throwIfEmpty() {
        if (isEmpty())
            throw new NoSuchElementException("Linked list is empty");
    }

}
