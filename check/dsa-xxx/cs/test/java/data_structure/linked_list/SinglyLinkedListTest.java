package data_structure.linked_list;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SinglyLinkedListTest implements LinkedListTest {

    @Override
    public LinkedList<Integer> create() {
        return new SinglyLinkedList<>();
    }

    @Override
    public LinkedList<Integer> create(Integer... values) {
        return new SinglyLinkedList<>(values);
    }

    @Override
    public Collection<LinkedList<Integer>> dataSet() {
        return Set.of(
                create(10),
                create(10, 20),
                create(10, 20, 30),
                create(10, 20, 30, 40),
                create(10, 20, 30, 40, 50)
        );
    }

    @Test
    void reverse() {
        var list = new SinglyLinkedList<>();
        assertDoesNotThrow(list::reverse);

        list = new SinglyLinkedList<>(10);
        assertEquals(10, list.getFirst());

        list = new SinglyLinkedList<>(10, 20);
        list.reverse();
        assertEquals(20, list.getFirst());
        assertEquals(10, list.getLast());

        list = new SinglyLinkedList<>(10, 20, 30);
        list.reverse();
        assertEquals(30, list.getFirst());
        assertEquals(10, list.getLast());
    }

}
