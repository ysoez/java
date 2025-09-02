package dsa.graph.tree;

class AvlTreeTest extends BinaryTreeTest {

    @Override
    BinaryTree<Integer> newBinaryTree(Integer... values) {
        var tree = new AvlTree<Integer>();
        for (Integer value : values)
            tree.insert(value);
        return tree;
    }

    //TODO: right rotations
    // 10 20 30 (right heavy)
    // 30 20 10 (left heavy)


//    @Test
//    void testAvlBalancing() {
//        // ~ right-heavy
//        var tree = new AvlTree();
//        tree.insert(1L);
//        tree.insert(2L);
//        tree.insert(3L);
//
////        assertTrue(tree.isBst());
//        assertTrue(Math.abs(tree.balanceFactor(tree.root)) <= 1);
////        assertEquals(1, tree.height());
//    }

}