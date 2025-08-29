package dsa.graph.tree;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;
import dsa.Utils;

import java.util.ArrayList;
import java.util.List;

import static dsa.Algorithm.Complexity.*;
import static dsa.Algorithm.Traversal.*;

abstract class BinaryTree<E extends Comparable<E>> implements Tree<E> {

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR), traversal = LEVEL_ORDER)
    public List<E> getAtDistance(int distance) {
        var list = new ArrayList<E>();
        getAtDistance(root(), distance, list);
        return list;
    }

    private void getAtDistance(Node<E> node, int distance, List<E> list) {
        if (node == null)
            return;
        if (distance == 0) {
            list.add(node.value());
            return;
        }
        getAtDistance(node.left(), distance - 1, list);
        getAtDistance(node.right(), distance - 1, list);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR), traversal = PRE_ORDER)
    public List<E> getAncestors(E value) {
        var list = new ArrayList<E>();
        getAncestors(root(), value, list);
        return list;
    }

    private boolean getAncestors(Node<E> node, E value, List<E> list) {
        //
        // ~ traverse the tree until the target value is found
        //
        if (node == null)
            return false;
        if (node.value().equals(value))
            return true;
        //
        // ~ if the target value in the left or right sub-trees is found, that means the current node (root)
        // ~ is one of the ancestors
        //
        if (getAncestors(node.left(), value, list) || getAncestors(node.right(), value, list)) {
            list.add(node.value());
            return true;
        }
        return false;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return root() == null;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = PRE_ORDER)
    public boolean isBst() {
        return isBst(root(), null, null);
    }

    private boolean isBst(Node<E> node, E min, E max) {
        if (node == null)
            return true;
        //
        // ~ check range
        //
        if (min != null && node.value().compareTo(min) <= 0)
            return false;
        if (max != null && node.value().compareTo(max) >= 0)
            return false;
        return isBst(node.left(), min, node.value()) && isBst(node.right(), node.value(), max);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR), traversal = PRE_ORDER)
    public boolean contains(E value) {
        return contains(root(), value);
    }

    private boolean contains(Node<E> node, E value) {
        if (node == null)
            return false;
        if (node.value().equals(value))
            return true;
        return contains(node.left(), value) || contains(node.right(), value);
    }

    @Override
    @Algorithm(complexity = @Algorithm.Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = PRE_ORDER)
    public boolean areSiblings(E first, E second) {
        return areSibling(root(), first, second);
    }

    private boolean areSibling(Node<E> node, E first, E second) {
        if (node == null)
            return false;
        var areSibling = false;
        if (node.left() != null && node.right() != null) {
            areSibling = (node.left().value().equals(first) && node.right().value().equals(second))
                    || (node.right().value().equals(first) && node.left().value().equals(second));
        }
        return areSibling || areSibling(node.left(), first, second) || areSibling(node.right(), first, second);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = POST_ORDER)
    public int size() {
        return size(root());
    }

    private int size(Node<E> node) {
        if (node == null)
            return 0;
        if (node.isLeaf())
            return 1;
        return 1 + size(node.left()) + size(node.right());
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = POST_ORDER)
    public int height() {
        return height(root());
    }

    private int height(Node<E> node) {
        if (node == null)
            return -1;
        if (node.isLeaf())
            return 0;
        return 1 + Math.max(height(node.left()), height(node.right()));
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = POST_ORDER)
    public E min() {
        return min(root());
    }

    private E min(Node<E> node) {
        throwIfEmpty();
        if (node.isLeaf())
            return node.value();
        var min = Utils.min(min(node.left()), min(node.right()));
        return Utils.min(node.value(), min);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = POST_ORDER)
    public E max() {
        return max(root());
    }

    private E max(Node<E> node) {
        throwIfEmpty();
        if (node.isLeaf())
            return node.value();
        return Utils.max(node.value(), Utils.max(max(node.left()), max(node.right())));
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = POST_ORDER)
    public int leavesCount() {
        return leavesCount(root());
    }

    private int leavesCount(Node<E> node) {
        if (node == null)
            return 0;
        if (node.isLeaf())
            return 1;
        return leavesCount(node.left()) + leavesCount(node.right());
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = PRE_ORDER)
    public boolean equals(BinaryTree<E> other) {
        if (other == null)
            return false;
        return equals(root(), other.root());
    }

    private boolean equals(Node<E> first, Node<E> second) {
        if (first == null && second == null)
            return true;
        if (first != null && second != null)
            return first.value().equals(second.value())
                    && equals(first.left(), second.left())
                    && equals(first.right(), second.right());
        return false;
    }

    void throwIfEmpty() {
        if (isEmpty())
            throw new EmptyTreeException();
    }

    abstract Node<E> root();

    interface Node<E> {

        Node<E> left();

        Node<E> right();

        E value();

        default boolean isLeaf() {
            return left() == null && right() == null;
        }
    }

}
