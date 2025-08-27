package dsa.graph.tree;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;
import static dsa.Algorithm.Traversal.POST_ORDER;

class BinarySearchTree extends BinaryTree {

    private BstNode root;

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public void insert(Long value) {
        var node = new BstNode(value);
        if (isEmpty()) {
            root = node;
            return;
        }
        var parent = root;
        while (true) {
            if (parent.value > value) {
                if (parent.left == null) {
                    parent.left = node;
                    break;
                }
                parent = parent.left;
            } else if (parent.value < value) {
                if (parent.right == null) {
                    parent.right = node;
                    break;
                }
                parent = parent.right;
            } else {
                break;
            }
        }
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public boolean find(Long value) {
        var current = root();
        while (current != null) {
            if (value < current.value()) {
                current = current.left();
            } else if (value > current.value()) {
                current = current.right();
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT), traversal = POST_ORDER)
    public Long min() {
        throwIfEmpty();
        return min(root());
    }

    private Long min(Node node) {
        if (node.isLeaf())
            return node.value();
        return min(node.left());
    }

    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    private Long minIterative() {
        var current = root;
        var last = current;
        while (current != null) {
            last = current;
            current = current.left;
        }
        return last.value;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = CONSTANT))
    public Long max() {
        throwIfEmpty();
        return max(root());
    }

    private Long max(Node root) {
        if (root.right() == null)
            return root.value();
        return max(root.right());
    }

    @Override
    Node root() {
        return root;
    }

    private static class BstNode implements Node {

        private final Long value;
        private BstNode left;
        private BstNode right;

        private BstNode(Long value) {
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
