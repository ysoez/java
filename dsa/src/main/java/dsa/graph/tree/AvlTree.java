package dsa.graph.tree;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LOGARITHMIC;
import static dsa.Algorithm.Traversal.PRE_ORDER;
import static dsa.Utils.lessThan;

class AvlTree<E extends Comparable<E>> extends BinarySearchTree<E> {

    AvlNode<E> root;

    @Override
    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = LOGARITHMIC), traversal = PRE_ORDER)
    public void insert(E value) {
        root = insert(root, value);
    }

    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = LOGARITHMIC), traversal = PRE_ORDER)
    private AvlNode<E> insert(AvlNode<E> node, E value) {
        if (node == null)
            return new AvlNode<>(value);
        if (lessThan(value, node.value))
            node.left = insert(node.left, value);
        else
            node.right = insert(node.right, value);
        setHeight(node);
        return balance(node);
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private AvlNode<E> balance(AvlNode<E> node) {
        if (isLeftHeavy(node)) {
            if (balanceFactor(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        } else if (isRightHeavy(node)) {
            if (balanceFactor(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private AvlNode<E> rotateLeft(AvlNode<E> node) {
        var newRoot = node.right;
        //
        // ~ rotate
        //
        node.right = newRoot.left;
        newRoot.left = node;
        //
        // ~ recalculate heights
        //
        setHeight(node);
        setHeight(newRoot);
        //
        // ~ propagate root
        //
        return newRoot;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private AvlNode<E> rotateRight(AvlNode<E> node) {
        var newRoot = node.left;
        //
        // ~ rotate
        //
        node.left = newRoot.right;
        newRoot.right = node;
        //
        // ~ recalculate heights
        //
        setHeight(node);
        setHeight(newRoot);
        //
        // ~ propagate root
        //
        return newRoot;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private void setHeight(AvlNode<E> node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private int height(AvlNode<E> node) {
        return node == null ? -1 : node.height;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private boolean isLeftHeavy(AvlNode<E> node) {
        return balanceFactor(node) > 1;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private boolean isRightHeavy(AvlNode<E> node) {
        return balanceFactor(node) < -1;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    int balanceFactor(AvlNode<E> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    Node<E> root() {
        return root;
    }

    private static class AvlNode<E> implements Node<E> {
        final E value;
        AvlNode<E> left;
        AvlNode<E> right;
        int height;

        AvlNode(E value) {
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
