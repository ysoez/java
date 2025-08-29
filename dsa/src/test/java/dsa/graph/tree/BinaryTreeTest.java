package dsa.graph.tree;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class BinaryTreeTest {

    abstract BinaryTree<Integer> newBinaryTree(Integer... values);

    @Test
    void testInsertAndFind() {
        var tree = newBinaryTree();

        tree.insert(7);
        assertTrue(tree.find(7));

        tree.insert(4);
        assertTrue(tree.find(4));

        tree.insert(9);
        assertTrue(tree.find(9));

        tree.insert(1);
        assertTrue(tree.find(1));

        tree.insert(6);
        assertTrue(tree.find(6));

        tree.insert(8);
        assertTrue(tree.find(8));

        tree.insert(10);
        assertTrue(tree.find(10));

        assertFalse(tree.find(Integer.MIN_VALUE));
        assertFalse(tree.find(20));
        assertFalse(tree.find(Integer.MAX_VALUE));
    }

    @Test
    void testIsEmpty() {
        var tree = newBinaryTree();
        assertTrue(tree.isEmpty());

        tree.insert(1);
        assertFalse(tree.isEmpty());
    }

    @Test
    void testSize() {
        var tree = newBinaryTree();
        assertEquals(0, tree.size());

        tree.insert(1);
        assertEquals(1, tree.size());

        tree.insert(2);
        tree.insert(3);
        assertEquals(3, tree.size());
    }

    @Test
    void testHeight() {
        var tree = newBinaryTree();
        assertEquals(-1, tree.height());

        tree.insert(7);
        assertEquals(0, tree.height());

        tree.insert(4);
        assertTrue(tree.height() >= 1);
    }

    @Test
    void testMin() {
        var tree = newBinaryTree();
        assertThrows(EmptyTreeException.class, tree::min);

        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        assertEquals(3, tree.min());
    }

    @Test
    void testMax() {
        var tree = newBinaryTree();
        assertThrows(EmptyTreeException.class, tree::max);

        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        assertEquals(7, tree.max());
    }

    @Test
    void testEquals() {
        var tree1 = newBinaryTree(7, 4, 9, 1, 6, 8, 10);
        var tree2 = newBinaryTree(7, 4, 9, 1, 6, 8);

        assertFalse(tree1.equals(tree2));

        tree2.insert(10);
        assertTrue(tree1.equals(tree2));

        assertFalse(tree1.equals(null));
    }

    @Test
    void testIsBst() {
        var tree = newBinaryTree(5, 3, 7, 2, 4);
        assertTrue(tree.isBst());
    }

    @Test
    void testGetAtDistance() {
        var tree = newBinaryTree(7, 4, 9, 1, 6, 8, 10);

        assertEquals(List.of(7), tree.getAtDistance(0));
        assertEquals(List.of(4, 9), tree.getAtDistance(1));
        assertEquals(List.of(1, 6, 8, 10), tree.getAtDistance(2));
        assertTrue(tree.getAtDistance(3).isEmpty());
        assertTrue(tree.getAtDistance(4).isEmpty());
    }

    @Test
    void testGetAncestors() {
        var tree = newBinaryTree(5, 3, 7, 2);

        List<Integer> ancestors = tree.getAncestors(2);
        assertTrue(ancestors.contains(5));
        assertTrue(ancestors.contains(3));
        assertFalse(ancestors.contains(7));
        assertFalse(ancestors.contains(2));

        assertTrue(tree.getAncestors(10).isEmpty());
    }

    @Test
    void testLeavesCount() {
        var tree = newBinaryTree();
        assertEquals(0, tree.leavesCount());

        tree.insert(1);
        assertEquals(1, tree.leavesCount());

        tree.insert(2);
        tree.insert(3);
        assertTrue(tree.leavesCount() >= 1);
    }

    @Test
    void testContains() {
        var tree = newBinaryTree(5, 3, 7);
        assertTrue(tree.contains(5));
        assertFalse(tree.contains(10));
    }

    @Test
    void testAreSiblings() {
        var tree = newBinaryTree(5, 3, 7, 2, 4);

        assertTrue(tree.areSiblings(3, 7));
        assertTrue(tree.areSiblings(2, 4));

        assertFalse(tree.areSiblings(5, 3));
        assertFalse(tree.areSiblings(5, 7));
        assertFalse(tree.areSiblings(5, 2));
        assertFalse(tree.areSiblings(5, 4));
        assertFalse(tree.areSiblings(3, 2));
        assertFalse(tree.areSiblings(3, 4));
        assertFalse(tree.areSiblings(3, 5));
        assertFalse(tree.areSiblings(7, 2));
        assertFalse(tree.areSiblings(7, 4));
        assertFalse(tree.areSiblings(7, 5));

        assertFalse(tree.areSiblings(10, 3));
    }


}