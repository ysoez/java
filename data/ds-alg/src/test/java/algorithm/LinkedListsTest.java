package algorithm;

import org.junit.jupiter.api.Test;
import util.UnaryNode;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListsTest {

    @Test
    void nthFromLast() {
        assertNull(LinkedLists.nthFromLast(null, 1));

        // head1 = 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> (null)
        var current = new UnaryNode<>(1, null);
        for (int i = 2; i < 8; i++) {
            current = new UnaryNode<>(i, current);
        }
        var head1 = current;

        assertNull(LinkedLists.nthFromLast(head1, -1));
        assertNull(LinkedLists.nthFromLast(head1, 0));
        assertEquals(1, LinkedLists.nthFromLast(head1, 1).value);
        assertEquals(5, LinkedLists.nthFromLast(head1, 5).value);
        assertEquals(7, LinkedLists.nthFromLast(head1, 7).value);
        assertNull(LinkedLists.nthFromLast(head1, 8));

        // head2 = 1 -> 2 -> 3 -> 4 -> (null)
        var current2 = new UnaryNode<>(4, null);
        for (int i = 3; i > 0; i--) {
            current2 = new UnaryNode<>(i, current2);
        }
        var head2 = current2;

        assertEquals(3, LinkedLists.nthFromLast(head2, 2).value);
        assertEquals(1, LinkedLists.nthFromLast(head2, 4).value);
        assertNull(LinkedLists.nthFromLast(head2, 5));
    }

}