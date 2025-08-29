package dsa.graph.tree;

class BinarySearchTreeTest extends BinaryTreeTest {

    @Override
    BinaryTree<Integer> newBinaryTree(Integer... values) {
        var tree = new BinarySearchTree<Integer>();
        for (Integer value : values)
            tree.insert(value);
        return tree;
    }

}