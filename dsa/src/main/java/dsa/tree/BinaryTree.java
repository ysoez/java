package dsa.tree;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import java.util.ArrayList;
import java.util.List;

import static dsa.Algorithm.Complexity.*;
import static dsa.Algorithm.Traversal.*;

abstract class BinaryTree implements Tree<Long> {

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR), traversal = LEVEL_ORDER)
    public List<Long> getAtDistance(int distance) {
        var list = new ArrayList<Long>();
        getAtDistance(root(), distance, list);
        return list;
    }

    private void getAtDistance(Node node, int distance, List<Long> list) {
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
    public List<Long> getAncestors(Long value) {
        var list = new ArrayList<Long>();
        getAncestors(root(), value, list);
        return list;
    }

    private boolean getAncestors(Node node, Long value, List<Long> list) {
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
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR), traversal = PRE_ORDER)
    public boolean isBst() {
        return isBst(root(), Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isBst(Node node, Long from, Long to) {
        if (node == null)
            return true;
        if (node.value() < from && node.value() > to) {
            return false;
        }
        return isBst(node.left(), from, node.value() - 1) && isBst(node.right(), node.value() + 1, to);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR), traversal = PRE_ORDER)
    public boolean contains(Long value) {
        return contains(root(), value);
    }

    private boolean contains(Node node, Long value) {
        if (node == null)
            return false;
        if (node.value().equals(value))
            return true;
        return contains(node.left(), value) || contains(node.right(), value);
    }

    @Override
    @Algorithm(complexity = @Algorithm.Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = PRE_ORDER)
    public boolean areSiblings(Long first, Long second) {
        return areSibling(root(), first, second);
    }

    private boolean areSibling(Node node, Long first, Long second) {
        if (node == null)
            return false;
        var areSibling = false;
        if (node.left() != null && node.right() != null) {
            areSibling = (node.left().value().equals(first) && node.right().value().equals(second)) ||
                    (node.right().value().equals(first) && node.left().value().equals(second));
        }
        return areSibling || areSibling(node.left(), first, second) || areSibling(node.right(), first, second);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = POST_ORDER)
    public int size() {
        return size(root());
    }

    private int size(Node node) {
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

    private int height(Node node) {
        if (node == null)
            return -1;
        if (node.isLeaf())
            return 0;
        return 1 + Math.max(height(node.left()), height(node.right()));
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = POST_ORDER)
    public int leavesCount() {
        return leavesCount(root());
    }

    private int leavesCount(Node node) {
        if (node == null)
            return 0;
        if (node.isLeaf())
            return 1;
        return leavesCount(node.left()) + leavesCount(node.right());
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = POST_ORDER)
    public Long min() {
        return min(root());
    }

    private Long min(Node node) {
        throwIfEmpty();
        if (node.isLeaf())
            return node.value();
        var min = Math.min(min(node.left()), min(node.right()));
        return Math.min(node.value(), min);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = POST_ORDER)
    public Long max() {
        return max(root());
    }

    private Long max(Node node) {
        throwIfEmpty();
        if (node.isLeaf())
            return node.value();
        return Math.max(node.value(), Math.max(max(node.left()), max(node.right())));
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LOGARITHMIC), traversal = PRE_ORDER)
    public boolean equals(BinaryTree other) {
        if (other == null)
            return false;
        return equals(root(), other.root());
    }

    private boolean equals(Node first, Node second) {
        if (first == null && second == null)
            return true;
        if (first != null && second != null) {
            var leftEqual = equals(first.left(), second.left());
            var rightEqual = equals(first.right(), second.right());
            return first.value().equals(second.value()) && leftEqual && rightEqual;
        }
        return false;
    }

    void throwIfEmpty() {
        if (isEmpty())
            throw new EmptyTreeException();
    }

    abstract Node root();

    interface Node {

        Node left();

        Node right();

        Long value();

        default boolean isLeaf() {
            return left() == null && right() == null;
        }
    }

}
