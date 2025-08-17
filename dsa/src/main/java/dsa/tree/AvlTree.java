package dsa.tree;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LOGARITHMIC;
import static dsa.Algorithm.Traversal.PRE_ORDER;

class AvlTree extends BinarySearchTree {

    AvlNode root;

    @Override
    @Algorithm(complexity = @Complexity(runtime = LOGARITHMIC, space = LOGARITHMIC), traversal = PRE_ORDER)
    public void insert(Long value) {
        root = insert(root, value);
    }

    private AvlNode insert(AvlNode node, Long value) {
        if (node == null)
            return new AvlNode(value);
        if (value < node.value)
            node.left = insert(node.left, value);
        else
            node.right = insert(node.right, value);
        setHeight(node);
        return balance(node);
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private AvlNode balance(AvlNode node) {
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

    private AvlNode rotateLeft(AvlNode node) {
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

    private AvlNode rotateRight(AvlNode node) {
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
    private void setHeight(AvlNode node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    private int height(AvlNode node) {
        return node == null ? -1 : node.height;
    }

    private boolean isLeftHeavy(AvlNode node) {
        return balanceFactor(node) > 1;
    }

    private boolean isRightHeavy(AvlNode node) {
        return balanceFactor(node) < -1;
    }

    int balanceFactor(AvlNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    @Override
    Node root() {
        return root;
    }

    private static class AvlNode implements Node {
        final Long value;
        AvlNode left;
        AvlNode right;
        int height;

        AvlNode(Long value) {
            this.value = value;
        }

        @Override
        public Node left() {
            return left;
        }

        @Override
        public Node right() {
            return right;
        }

        @Override
        public Long value() {
            return value;
        }
    }

}
