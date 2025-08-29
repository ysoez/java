package dsa.graph.tree;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Complexity.*;
import static dsa.Algorithm.Traversal.POST_ORDER;

class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {

    private BstNode<E> root;

    @Override
    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = CONSTANT))
    public void insert(E value) {
        var node = new BstNode<>(value);
        if (isEmpty()) {
            root = node;
            return;
        }
        var current = root;
        //
        // ~ break when a parent is found
        //
        while (true) {
            if (current.value.compareTo(value) > 0) {
                if (current.left == null) {
                    current.left = node;
                    break;
                }
                current = current.left;
            } else if (current.value.compareTo(value) < 0) {
                if (current.right == null) {
                    current.right = node;
                    break;
                }
                current = current.right;
            } else {
                break;
            }
        }
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = CONSTANT))
    public boolean find(E value) {
        var current = root();
        while (current != null)
            if (value.compareTo(current.value()) < 0)
                current = current.left();
            else if (value.compareTo(current.value()) > 0)
                current = current.right();
            else
                return true;
        return false;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = CONSTANT), traversal = POST_ORDER)
    public E min() {
        throwIfEmpty();
        return min(root());
    }

    private E min(Node<E> node) {
        if (node.isLeaf())
            return node.value();
        return min(node.left());
    }

    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = CONSTANT), traversal = POST_ORDER)
    private E minIterative() {
        if (root == null)
            throw new EmptyTreeException();
        var current = root;
        var last = current;
        while (current != null) {
            last = current;
            current = current.left;
        }
        return last.value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = CONSTANT))
    public E max() {
        throwIfEmpty();
        return max(root());
    }

    private E max(Node<E> root) {
        if (root.right() == null)
            return root.value();
        return max(root.right());
    }

    @Override
    Node<E> root() {
        return root;
    }

    private static class BstNode<E> implements Node<E> {

        private final E value;
        private BstNode<E> left;
        private BstNode<E> right;

        private BstNode(E value) {
            this.value = value;
        }

        @Override
        public Node<E> left() {
            return left;
        }

        @Override
        public Node<E> right() {
            return right;
        }

        @Override
        public E value() {
            return value;
        }
    }

}
