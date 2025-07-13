package dsa.tree;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testInsertAndFind(Tree<Long> tree) {
        tree.insert(5L);
        tree.insert(3L);
        tree.insert(7L);

        assertTrue(tree.find(5L));
        assertTrue(tree.find(3L));
        assertTrue(tree.find(7L));

        assertFalse(tree.find(Long.MIN_VALUE));
        assertFalse(tree.find(10L));
        assertFalse(tree.find(Long.MAX_VALUE));
    }

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testIsEmpty(Tree<Long> tree) {
        assertTrue(tree.isEmpty());

        tree.insert(1L);

        assertFalse(tree.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testSize(Tree<Long> tree) {
        assertEquals(0, tree.size());

        tree.insert(1L);
        assertEquals(1, tree.size());

        tree.insert(2L);
        tree.insert(3L);
        assertEquals(3, tree.size());
    }

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testHeight(Tree<Long> tree) {
        assertEquals(-1, tree.height(), "Empty tree should have height -1");
        tree.insert(1L);
        assertEquals(0, tree.height(), "Single node tree should have height 0");
        tree.insert(2L);
        assertTrue(tree.height() >= 1, "Tree with two nodes should have height >= 1");
    }

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testLeavesCount(Tree<Long> tree) {
        assertEquals(0, tree.leavesCount(), "Empty tree should have 0 leaves");
        tree.insert(1L);
        assertEquals(1, tree.leavesCount(), "Single node tree should have 1 leaf");
        tree.insert(2L);
        tree.insert(3L);
        assertTrue(tree.leavesCount() >= 1, "Tree with multiple nodes should have at least 1 leaf");
    }

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testMin(Tree<Long> tree) {
        assertThrows(EmptyTreeException.class, tree::min, "Min on empty tree should throw EmptyTreeException");
        tree.insert(5L);
        tree.insert(3L);
        tree.insert(7L);
        assertEquals(3L, tree.min(), "Min should return smallest value 3");
    }

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testMax(Tree<Long> tree) {
        assertThrows(EmptyTreeException.class, tree::max, "Max on empty tree should throw EmptyTreeException");
        tree.insert(5L);
        tree.insert(3L);
        tree.insert(7L);
        assertEquals(7L, tree.max(), "Max should return largest value 7");
    }

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testContains(Tree<Long> tree) {
        tree.insert(5L);
        tree.insert(3L);
        tree.insert(7L);
        assertTrue(tree.contains(5L));
        assertFalse(tree.contains(10L));
    }

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testGetAtDistance(Tree<Long> tree) {
        tree.insert(5L);
        tree.insert(3L);
        tree.insert(7L);
        tree.insert(2L);
        tree.insert(4L);

        Collection<Long> distance0 = tree.getAtDistance(0);
        assertTrue(distance0.contains(5L), "Distance 0 should contain root value 5");
        assertEquals(1, distance0.size(), "Distance 0 should have 1 node");

        Collection<Long> distance1 = tree.getAtDistance(1);
        assertTrue(distance1.contains(3L));
        assertTrue(distance1.contains(7L));
        assertEquals(2, distance1.size());

        Collection<Long> distance2 = tree.getAtDistance(2);
        assertTrue(distance2.contains(2L) || distance2.contains(4L));
    }

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testGetAncestors(Tree<Long> tree) {
        tree.insert(5L);
        tree.insert(3L);
        tree.insert(7L);
        tree.insert(2L);

        List<Long> ancestors = (List<Long>) tree.getAncestors(2L);
        assertTrue(ancestors.contains(5L), "Ancestors of 2 should include 5");
        assertTrue(ancestors.contains(3L), "Ancestors of 2 should include 3");
        assertFalse(ancestors.contains(7L), "Ancestors of 2 should not include 7");
        assertFalse(ancestors.contains(2L), "Ancestors of 2 should not include itself");

        List<Long> noAncestors = (List<Long>) tree.getAncestors(10L);
        assertTrue(noAncestors.isEmpty(), "Non-existent value should have no ancestors");
    }

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testAreSiblings(Tree<Long> tree) {
        tree.insert(5L);
        tree.insert(3L);
        tree.insert(7L);
        tree.insert(2L);
        tree.insert(4L);

        assertTrue(tree.areSiblings(3L, 7L));
        assertTrue(tree.areSiblings(2L, 4L));

        assertFalse(tree.areSiblings(5L, 3L));
        assertFalse(tree.areSiblings(5L, 7L));
        assertFalse(tree.areSiblings(5L, 2L));
        assertFalse(tree.areSiblings(5L, 4L));
        assertFalse(tree.areSiblings(3L, 2L));
        assertFalse(tree.areSiblings(3L, 4L));
        assertFalse(tree.areSiblings(3L, 5L));
        assertFalse(tree.areSiblings(7L, 2L));
        assertFalse(tree.areSiblings(7L, 4L));
        assertFalse(tree.areSiblings(7L, 5L));

        assertFalse(tree.areSiblings(10L, 3L));
    }

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testIsBst(Tree<Long> tree) {
        tree.insert(5L);
        tree.insert(3L);
        tree.insert(7L);
        tree.insert(2L);
        tree.insert(4L);

        assertTrue(tree.isBst());
    }

    @ParameterizedTest
    @MethodSource("treeImplementations")
    void testIsNotBst(Tree<Long> tree) {
        tree.insert(5L);
        tree.insert(3L);
        tree.insert(7L);
        tree.insert(2L);
        tree.insert(4L);

        assertTrue(tree.isBst());
    }

    static Stream<Tree<Long>> treeImplementations() {
        return Stream.of(
                new BinarySearchTree(),
                new AvlTree()
        );
    }

}