package dsa.graph.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvlTreeTest {

    @Test
    void testAvlBalancing() {
        // ~ right-heavy
        var tree = new AvlTree();
        tree.insert(1L);
        tree.insert(2L);
        tree.insert(3L);

        assertTrue(tree.isBst());
        assertTrue(Math.abs(tree.balanceFactor(tree.root)) <= 1);
        assertEquals(1, tree.height());
    }

}