package dsa.list.linked;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;
import dsa.Node;
import dsa.array.Arrays;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;
import static java.util.Collections.emptyList;

public class SinglyLinkedList<E> implements LinkedList<E> {

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
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
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
        getNode(index).value = value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E get(int index) {
        return getNode(index).value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E getFromEnd(int offset) {
        if (offset < 0)
            throw new IllegalArgumentException("negative offset");
        if (isEmpty())
            throw new NoSuchElementException();
        var slow = head;
        var fast = slow;
        for (int i = 0; i < offset - 1; i++) {
            fast = fast.next;
            if (fast == null)
                throw new IllegalArgumentException("offset cannot be greater than size");
        }
        while (fast != tail) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow.value;
    }

    @Override
    public Collection<E> getMiddle() {
        if (isEmpty())
            return emptyList();
        var slow = head;
        var fast = slow;
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return fast != tail ? List.of(slow.value, slow.next.value) : List.of(slow.value);
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
        var index = 0;
        var current = head;
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
    public E deleteFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        var toRemove = head;
        if (head == tail)
            head = tail = null;
        else {
            head = toRemove.next;
        }
        toRemove.next = null;
        size--;
        return toRemove.value;
    }

    @Override
    public E deleteAt(int index) {
        if (isEmpty())
            throw new NoSuchElementException();

        return null;
//        if (head == tail) {
//            var toRemove = head;
//            head = tail = null;
//            return toRemove.value;
//        }
//        var current = head;
//        for (int i = 0; i < index - 1; i++) {
//            current = current.next;
//            if (current == null) {
//                throw new IllegalArgumentException();
//            }
//        }
//        // 10
//        var toDelete = current.next;
//        if (toDelete != null)
//            current.next = toDelete.next;
//        return toDelete != null ? toDelete.value : null;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public E deleteLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        var toRemove = tail;
        if (head == tail) {
            head = tail = null;
        } else {
            var beforeLast = previousOf(tail);
            beforeLast.next = null;
            tail = beforeLast;
        }
        size--;
        return toRemove.value;
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    private Node<E> previousOf(Node<E> node) {
        var current = head;
        while (current != null) {
            if (current.next == node)
                return current;
            current = current.next;
        }
        return null;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public void clear() {
        for (Node<E> current = head; current != null; ) {
            var next = current.next;
            current.next = null;
            current = next;
        }
        head = tail = null;
        size = 0;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public E[] toArray() {
        E[] arr = Arrays.newArray(size);
        var current = head;
        var index = 0;
        while (current != null) {
            arr[index++] = current.value;
            current = current.next;
        }
        return arr;
    }

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

    private Node<E> getNode(int index) {
        checkIndex(index);
        var current = head;
        for (int i = 0; i < index; i++)
            current = current.next;
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
//
//    private static class Node<E> {
//        E value;
//        Node<E> next;
//
//        public Node(E value) {
//            this.value = value;
//        }
//    }
//
//    @Override
//    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
//    public void insertFirst(E value) {
//        var node = new Node<>(value);
//        if (isEmpty())
//            head = tail = node;
//        else {
//            node.next = head;
//            head = node;
//        }
//        size++;
//    }
//
//    @Override
//    public void insertAt(int index, E value) {
//
//    }
//
//    @Override
//    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
//    public void insertLast(E value) {
//        var node = new Node<>(value);
//        if (isEmpty())
//            head = tail = node;
//        else {
//            tail.next = node;
//            tail = node;
//        }
//        size++;
//    }
//
//    @Override
//    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
//    public int size() {
//        return size;
//    }
//
//    @Override
//    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
//    public boolean isEmpty() {
//        return head == null;
//    }
//
//    @Override
//    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
//    public int indexOf(E value) {
//        var current = head;
//        var index = 0;
//        while (current != null) {
//            if (current.value.equals(value))
//                return index;
//            current = current.next;
//            index++;
//        }
//        return -1;
//    }
//
//    @Override
//    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
//    public void reverse() {
//        if (isEmpty())
//            return;
//        var prev = head;
//        var current = head.next;
//        while (current != null) {
//            var next = current.next;
//            current.next = prev;
//            prev = current;
//            current = next;
//        }
//        tail = head;
//        tail.next = null;
//        head = prev;
//    }
//
//    @Override
//    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
//    public E[] toArray() {
//        E[] array = Arrays.newArray(size);
//        var current = head;
//        var index = 0;
//        while (current != null) {
//            array[index++] = current.value;
//            current = current.next;
//        }
//        return array;
//    }
//
//    public Collection<E> getMiddle() {
//        if (isEmpty())
//            throw new EmptyListException();
//        var slow = head;
//        var fast = head;
//        while (fast != tail && fast.next != tail) {
//            slow = slow.next;
//            fast = fast.next.next;
//        }
//        return fast == tail ? java.util.List.of(slow.value) : java.util.List.of(slow.value, slow.next.value);
//    }
//
//
//
//    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
//    private Node<E> findPrev(Node<E> node) {
//        var current = head;
//        while (current != null) {
//            if (current.next == node)
//                return current;
//            current = current.next;
//        }
//        return null;
//    }
//
//    private void checkIndex(int index) {
//        if (isInvalidIndex(index, size))
//            throw new IndexOutOfBoundsException();
//    }

}
