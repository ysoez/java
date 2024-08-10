package data_structure.tree;

import lombok.RequiredArgsConstructor;

import static java.lang.Math.max;

class AvlTree<T extends Comparable<T>> implements Tree<T> {

    private AvlTreeNode<T> root;

    @Override
    public void insert(T value) {
        root = insert(root, value);
    }

    private AvlTreeNode<T> insert(AvlTreeNode<T> node, T value) {
        if (node == null)
            return new AvlTreeNode<>(value);
        if (node.value.compareTo(value) < 0)
            node.left = insert(node.left, value);
        else
            node.right = insert(node.right, value);
        setHeight(node);
        return balance(node);
    }

    private int height(AvlTreeNode<T> node) {
        return node == null ? -1 : node.height;
    }

    @Override
    public boolean contains(T value) {
        return false;
    }

    private AvlTreeNode<T> balance(AvlTreeNode<T> node) {
        if (isLeftHeavy(node)) {
            if (balanceFactor(node.left) < 0 )
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        } else if (isRightHeavy(node)) {
            if (balanceFactor(node.right) > 0 )
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private boolean isLeftHeavy(AvlTreeNode<T> node) {
        return balanceFactor(node) > 1;
    }

    private boolean isRightHeavy(AvlTreeNode<T> node) {
        return balanceFactor(node) < -1;
    }

    private int balanceFactor(AvlTreeNode<T> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private AvlTreeNode<T> rotateLeft(AvlTreeNode<T> node) {
        var newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        setHeight(node);
        setHeight(newRoot);
        return newRoot;
    }

    private AvlTreeNode<T> rotateRight(AvlTreeNode<T> node) {
        var newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        setHeight(node);
        setHeight(newRoot);
        return newRoot;
    }

    private void setHeight(AvlTreeNode<T> node) {
        node.height = max(height(node.left), height(node.right)) + 1;
    }

    @RequiredArgsConstructor
    static class AvlTreeNode<T> {
        AvlTreeNode<T> left;
        AvlTreeNode<T> right;
        int height;
        final T value;

        @Override
        public String toString() {
            return value.toString();
        }
    }

}
