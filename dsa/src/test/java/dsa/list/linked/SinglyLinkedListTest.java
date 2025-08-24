package dsa.list.linked;

import dsa.list.EmptyListException;
import dsa.list.List;
import dsa.list.ListTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SinglyLinkedListTest extends ListTest {

    @Override
    public List<Integer> newList() {
        return new SinglyLinkedList<>();
    }

    @Nested
    class TestMiddle {
        @Test
        void testEmptyList() {
            var list = new SinglyLinkedList<>();
            assertThrows(EmptyListException.class, list::getMiddle);
        }
        @Test
        void testSingletonList() {
            var list = new SinglyLinkedList<>();
            list.insertLast(1);
            assertEquals(java.util.List.of(1), list.getMiddle());
        }
        @Test
        void testEvenList() {
            var list = new SinglyLinkedList<>();
            list.insertLast(1);
            list.insertLast(2);
            assertEquals(java.util.List.of(1, 2), list.getMiddle());
            list.insertLast(3);
            list.insertLast(4);
            assertEquals(java.util.List.of(2, 3), list.getMiddle());
        }
        @Test
        void testOddList() {
            var list = new SinglyLinkedList<>();
            list.insertLast(1);
            list.insertLast(2);
            list.insertLast(3);
            assertEquals(java.util.List.of(2), list.getMiddle());
        }
    }

}