package algorithm;

import org.junit.jupiter.api.Test;
import util.BinaryNode;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TreesTest {

    @Test
    void isBst() {
        // head1 = 0
        //        / \
        //       1   2
        //      /\   /\
        //     3  4 5  6
        HashMap<Integer, int[]> mapping1 = new HashMap<>();
        int[] childrenA = {1, 2};
        int[] childrenB = {3, 4};
        int[] childrenC = {5, 6};
        mapping1.put(0, childrenA);
        mapping1.put(1, childrenB);
        mapping1.put(2, childrenC);
        var head1 = createTree(mapping1, 0);
        assertFalse(Trees.isBst(head1));

        //  head2 = 3
        //        /   \
        //       1     4
        //      /\    / \
        //     0  2  5   6
        HashMap<Integer, int[]> mapping2 = new HashMap<>();
        int[] childrenD = {1, 4};
        int[] childrenE = {0, 2};
        int[] childrenF = {5, 6};
        mapping2.put(3, childrenD);
        mapping2.put(1, childrenE);
        mapping2.put(4, childrenF);
        var head2 = createTree(mapping2, 3);
        assertFalse(Trees.isBst(head2));

        //  head3 = 3
        //        /   \
        //       1     5
        //      /\    / \
        //     0  2  4   6
        HashMap<Integer, int[]> mapping3 = new HashMap<>();
        int[] childrenG = {1, 5};
        int[] childrenH = {0, 2};
        int[] childrenI = {4, 6};
        mapping3.put(3, childrenG);
        mapping3.put(1, childrenH);
        mapping3.put(5, childrenI);
        var head3 = createTree(mapping3, 3);
        assertTrue(Trees.isBst(head3));

        //  head4 = 3
        //        /   \
        //       1     5
        //      /\
        //     0  4
        HashMap<Integer, int[]> mapping4 = new HashMap<>();
        int[] childrenJ = {1, 5};
        int[] childrenK = {0, 4};
        mapping4.put(3, childrenJ);
        mapping4.put(1, childrenK);
        var head4 = createTree(mapping4, 3);
        assertFalse(Trees.isBst(head4));
    }

    @Test
    void lca() {
        assertNull(Trees.lca(null, 3, 0));

        // head1 = 0
        //        / \
        //       1   2
        //      /\   /\
        //     3  4 5  6
        HashMap<Integer, int[]> mapping1 = new HashMap<>();
        int[] childrenA = {1, 2};
        int[] childrenB = {3, 4};
        int[] childrenC = {5, 6};
        mapping1.put(0, childrenA);
        mapping1.put(1, childrenB);
        mapping1.put(2, childrenC);
        var head1 = createTree(mapping1, 0);

        assertEquals(0, Trees.lca(head1, 1, 5));
        assertEquals(1, Trees.lca(head1, 3, 1));
        assertEquals(1, Trees.lca(head1, 1, 4));
        assertEquals(0, Trees.lca(head1, 0, 5));

        //  head2 = 5
        //        /   \
        //       1     4
        //      /\    / \
        //     3  8  9  2
        //    /\
        //   6  7
        HashMap<Integer, int[]> mapping2 = new HashMap<>();
        int[] childrenD = {1, 4};
        int[] childrenE = {3, 8};
        int[] childrenF = {9, 2};
        int[] childrenG = {6, 7};
        mapping2.put(5, childrenD);
        mapping2.put(1, childrenE);
        mapping2.put(4, childrenF);
        mapping2.put(3, childrenG);
        var head2 = createTree(mapping2, 5);

        assertEquals(5, Trees.lca(head2, 4, 7));
        assertEquals(3, Trees.lca(head2, 3, 3));
        assertEquals(1, Trees.lca(head2, 8, 7));
        assertNull(Trees.lca(head2, 3, 0));
        assertNull(Trees.lca(null, 3, 0));
    }

    // example: {0: [1, 2]} means that 0's left child is 1, and its right child is 2
    private static BinaryNode<Integer> createTree(HashMap<Integer, int[]> mapping, int headValue) {
        var head = new BinaryNode<>(headValue, null, null);
        var nodes = new HashMap<Integer, BinaryNode<Integer>>();
        nodes.put(headValue, head);
        for (Integer key : mapping.keySet()) {
            int[] value = mapping.get(key);
            var leftChild = new BinaryNode<>(value[0], null, null);
            var rightChild = new BinaryNode<>(value[1], null, null);
            nodes.put(value[0], leftChild);
            nodes.put(value[1], rightChild);
        }
        for (Integer key : mapping.keySet()) {
            int[] value = mapping.get(key);
            nodes.get(key).left = nodes.get(value[0]);
            nodes.get(key).right = nodes.get(value[1]);
        }
        return head;
    }

}