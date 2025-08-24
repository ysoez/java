package dsa.list.linked;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;
import dsa.array.Arrays;
import dsa.list.EmptyListException;
import dsa.list.List;
import dsa.list.Lists;

import java.util.Collection;
import java.util.NoSuchElementException;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;
import static dsa.list.Lists.isInvalidIndex;

class SinglyLinkedList<E> implements List<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void insertFirst(E value) {
        var node = new Node<>(value);
        if (isEmpty())
            head = tail = node;
        else {
            node.next = head;
            head = node;
        }
        size++;
    }

    @Override
    public void insertAt(int index, E value) {

    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void insertLast(E value) {
        var node = new Node<>(value);
        if (isEmpty())
            head = tail = node;
        else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public void set(int index, E value) {
        checkIndex(index);
        getNode(index).value = value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E getFromEnd(int offset) {
        if (isEmpty())
            throw new EmptyListException();
        var slow = head;
        var fast = head;
        for (int i = 0; i < offset - 1; i++) {
            fast = fast.next;
            if (fast == null)
                throw new IllegalArgumentException("offset is bigger than size");
        }
        while (fast != tail) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow.value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int size() {
        return size;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public int indexOf(E value) {
        var current = head;
        var index = 0;
        while (current != null) {
            if (current.value.equals(value))
                return index;
            current = current.next;
            index++;
        }
        return -1;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E deleteFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Node<E> toRemove = head;
        if (head == tail)
            head = tail = null;
        else
            head = head.next;
        toRemove.next = null;
        size--;
        return toRemove.value;
    }

    @Override
    public E deleteAt(int index) {
        return null;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E deleteLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        var toRemove = tail;
        if (head == tail)
            head = tail = null;
        else {
            var beforeLast = findPrev(tail);
            beforeLast.next = null;
            tail = beforeLast;
        }
        size--;
        return toRemove.value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public void reverse() {
        if (isEmpty())
            return;
        var prev = head;
        var current = head.next;
        while (current != null) {
            var next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        tail = head;
        tail.next = null;
        head = prev;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public E[] toArray() {
        E[] array = Arrays.newArray(size);
        var current = head;
        var index = 0;
        while (current != null) {
            array[index++] = current.value;
            current = current.next;
        }
        return array;
    }

    public Collection<E> getMiddle() {
        if (isEmpty())
            throw new EmptyListException();
        var slow = head;
        var fast = head;
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return fast == tail ? java.util.List.of(slow.value) : java.util.List.of(slow.value, slow.next.value);
    }

    public boolean hasCycle() {
        if (isEmpty() || head == tail)
            return false;
        var slow = head;
        var fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)
                return true;
        }
        return false;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    private Node<E> findPrev(Node<E> node) {
        var current = head;
        while (current != null) {
            if (current.next == node)
                return current;
            current = current.next;
        }
        return null;
    }

    private void checkIndex(int index) {
        if (isInvalidIndex(index, size))
            throw new IndexOutOfBoundsException();
    }

    private Node<E> getNode(int index) {
        var current = head;
        for (int i = 0; i < index; i++)
            current = current.next;
        return current;
    }

    private static class Node<E> {
        E value;
        Node<E> next;

        Node(E value) {
            this.value = value;
        }
    }

}
