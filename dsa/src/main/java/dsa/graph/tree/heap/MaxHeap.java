package dsa.graph.tree.heap;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.lang.reflect.Array;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LOGARITHMIC;
import static dsa.Utils.greaterThan;
import static dsa.Utils.greaterThanOrEqual;
import static dsa.array.Arrays.swap;
import static dsa.graph.tree.heap.Heaps.*;

public class MaxHeap<E extends Comparable<E>> implements Heap<E> {

    private static final int DEFAULT_MAX_SIZE = 10;
    final E[] elements;
    private int size;

    public MaxHeap() {
        this(DEFAULT_MAX_SIZE);
    }

    @SuppressWarnings("unchecked")
    public MaxHeap(int maxSize) {
        elements = (E[]) Array.newInstance(Comparable.class, maxSize);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = CONSTANT))
    public void insert(E value) {
        if (isFull())
            throw new FullHeapException();
        elements[size++] = value;
        bubbleUp();
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = CONSTANT))
    public E remove() {
        throwIfEmpty();
        var root = elements[0];
        //
        // ~ move last element to the top
        //
        elements[0] = elements[--size];
        bubbleDown();
        return root;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int size() {
        return size;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isFull() {
        return elements.length == size;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public E max() {
        throwIfEmpty();
        return elements[0];
    }

    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = CONSTANT))
    private void bubbleUp() {
        //
        // ~ resolve new element index (incremented after insert)
        //
        var index = size - 1;
        //
        // ~ propagate bigger values to the top
        // ~ ensure index > 0 for a valid parentIndex (avoid ArrayStoreException)
        //
        while (index > 0 && greaterThan(elements[index], elements[parentIndex(index)])) {
            swap(elements, index, parentIndex(index));
            index = parentIndex(index);
        }
    }

    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = CONSTANT))
    private void bubbleDown() {
        var index = 0;
        while (index <= size && !isValidParent(index)) {
            var largerChildIndex = largerChildIndex(index);
            swap(elements, index, largerChildIndex);
            index = largerChildIndex;
        }
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private int largerChildIndex(int index) {
        //
        // ~ in complete tree no left child = no children
        //
        if (!hasLeftChild(index)) {
            return index;
        }
        if (!hasRightChild(index)) {
            return leftChildIndex(index);
        }
        //
        // ~ node has left and right child
        //
        return greaterThan(leftChild(index), rightChild(index)) ? leftChildIndex(index) : rightChildIndex(index);
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private boolean hasLeftChild(int index) {
        return leftChildIndex(index) <= size;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private boolean hasRightChild(int index) {
        return rightChildIndex(index) <= size;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private boolean isValidParent(int index) {
        //
        // ~ in complete tree no left child = no children
        //
        if (!hasLeftChild(index))
            return true;
        var parent = elements[index];
        var isValid = greaterThanOrEqual(parent, leftChild(index));
        if (hasRightChild(index))
            isValid &= greaterThanOrEqual(parent, rightChild(index));
        return isValid;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private E leftChild(int index) {
        return elements[leftChildIndex(index)];
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private E rightChild(int index) {
        return elements[rightChildIndex(index)];
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void throwIfEmpty() {
        if (isEmpty())
            throw new EmptyHeapException();
    }

}
