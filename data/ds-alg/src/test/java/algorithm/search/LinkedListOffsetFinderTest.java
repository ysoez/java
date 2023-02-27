package algorithm.search;

import data_structure.linked_list.UnaryNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LinkedListOffsetFinderTest {

    @Test
    void search() {
        var finder = new LinkedListOffsetFinder<Integer>();

        assertThrows(IllegalArgumentException.class, () -> finder.search(null, 1));

        var node = new UnaryNode<>(10);
        var current = node;
        for (int i = 2; i < 6; i++) {
            var next = new UnaryNode<>(i * 10);
            current.next(next);
            current = next;
        }

        assertEquals(50, finder.search(node, -1).value());
        assertEquals(50, finder.search(node, 0).value());
        assertEquals(50, finder.search(node, 1).value());
        assertEquals(40, finder.search(node, 2).value());
        assertEquals(30, finder.search(node, 3).value());
        assertEquals(20, finder.search(node, 4).value());
        assertEquals(10, finder.search(node, 5).value());

        assertThrows(IllegalArgumentException.class, () -> finder.search(node, 6));
    }

}