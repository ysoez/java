package data_structure.tree;

import lombok.RequiredArgsConstructor;
import util.Traversal;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static util.Traversal.Type.PRE_ORDER;

//[10, 5, 15, 6, 1, 8, 12, 18, 17]

//Tree tree = new Tree();
//        tree.insert(7);
//        tree.insert(4);
//        tree.insert(9);
//        tree.insert(1);
//        tree.insert(6);
//        tree.insert(8);
//        tree.insert(10);
class BinaryTree<T extends Comparable<T>> implements Tree<T> {

    private TreeNode<T> root;

    @Override
    public void insert(T value) {
        var node = new TreeNode<>(value);
        if (isEmpty()) {
            root = node;
            return;
        }
        var current = root;
        while (true) {
            if (current.value.compareTo(value) > 0) {
                if (current.right == null) {
                    current.right = node;
                    break;
                }
                current = current.right;
            } else if (root.value.compareTo(value) < 0) {
                if (current.left == null) {
                    current.left = node;
                    break;
                }
                current = current.left;
            }
        }
    }

    @Override
    public boolean contains(T value) {
        if (isEmpty())
            return false;
        var current = root;
        while (current != null) {
            if (current.value.compareTo(value) == 0) {
                return true;
            }
            if (current.value.compareTo(value) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    public void traversePreOrder() {
        preOrder(root);
    }

    private void preOrder(TreeNode<T> node) {
        if (node == null)
            return;
        System.out.println(node.value);
        preOrder(root.left);
        preOrder(root.right);
    }

    public void traverseInOrder() {
        traverseInOrder(root);
    }

    private void traverseInOrder(TreeNode<T> node) {
        if (node == null)
            return;
        preOrder(root.left);
        System.out.println(node.value);
        preOrder(root.right);
    }

    public void traversePostOrder() {
        traversePostOrder(root);
    }

    private void traversePostOrder(TreeNode<T> node) {
        if (node == null)
            return;
        preOrder(root.left);
        preOrder(root.right);
        System.out.println(node.value);
    }

    public int height() {
        return height(root);
    }

    private int height(TreeNode<T> node) {
        if (node == null)
            return -1;
        if (node.isLeaf())
            return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public T min() {
        return min(root);
    }

    // for binary tree o(n)
    public T min(TreeNode<T> node) {
        if (node.isLeaf())
            return node.value;
        var leftMin = min(node.left);
        var rightMin = min(node.right);
        return min(node.value, min(leftMin, rightMin));
    }

    //todo: my impl is valid???
    public T minInBst(TreeNode<T> node) {
        if (node.isLeaf())
            return node.value;
        return minInBst(node.left);
    }

    // O(log n)
    public T minInBst() {
        if (isEmpty())
            throw new NoSuchElementException();
        var current = root;
        var last = root;
        while (current != null) {
            last = current;
            current = current.left;
        }
        return last.value;
     }

    private T min(T t1, T t2) {
        return t1.compareTo(t2) < 0 ? t1 : t2;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean isEqual(BinaryTree<T> tree) {
        if (tree == null)
            return false;
        return isEqual(root, tree.root);
    }

    @Traversal(PRE_ORDER)
    private boolean isEqual(TreeNode<T> node1, TreeNode<T> node2) {
        if (node1 == null && node2 == null)
            return true;
        if (node1 == null || node2 == null)
            return false;
        return node1.value.compareTo(node2.value) == 0 && isEqual(node1.left, node2.left) && isEqual(node1.right, node2.right);
    }

    //todo: isBinarySearchTree()
    // validate BST
    // bad idea to check left and right children - visiting nodes multiple times
    // use ranges
//    public boolean isBst() {
//        return isBst(root, MIN_VALUE, MAX_VALUE);
//    }


    //todo: does not work with generics
//    @Traversal(PRE_ORDER)
//    private boolean isBst(TreeNode<Integer> node, Integer min, Integer max) {
//        if (node == null)
//            return true;
//        if (node.value < min  || node.value > max)
//            return false;
//        return isBst(node.left, min, node.value - 1) && isBst(node.right, node.value + 1, max);
//    }

    public List<T> getAtDistance(int distance) {
        var list = new ArrayList<T>();
        getAtDistance(root, distance, list);
        return list;
    }

    @Traversal(PRE_ORDER)
    private void getAtDistance(TreeNode<T> node, int distance, List<T> list) {
        if (root == null)
            return;
        if (distance == 0) {
            list.add(node.value);
            return;
        }
        getAtDistance(node.left, distance - 1, list);
        getAtDistance(node.right, distance - 1, list);
    }

    // level order
    public void traverseLevelOrder() {
        for (var i = 0; i <= height(); i++) {
            for (T t : getAtDistance(i)) {
                System.out.println(t);
            }
        }
    }

    @RequiredArgsConstructor
    static class TreeNode<T> {
        TreeNode<T> left;
        TreeNode<T> right;
        final T value;

        boolean isLeaf() {
            return left == null && right == null;
        }
    }

}
