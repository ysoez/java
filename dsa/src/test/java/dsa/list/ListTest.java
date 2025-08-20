package dsa.list;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public abstract class ListTest {

    private final List<Integer> list = newList();

    public abstract List<Integer> newList();

    @Test
    void testInsertFirst() {
        list.insertFirst(10);
        list.insertFirst(5);

        assertEquals(2, list.size());
        assertEquals(5, list.get(0));
        assertEquals(10, list.get(1));
    }

    @Test
    void testInsertLast() {
        list.insertLast(1);
        list.insertLast(2);
        list.insertLast(3);

        assertEquals(3, list.size());
        assertEquals(3, list.get(2));
    }

    @Test
    void testInsertAtMiddle() {
        list.insertLast(1);
        list.insertLast(3);

        list.insertAt(1, 2);

        assertEquals(3, list.size());
        assertEquals(2, list.get(1));
    }

    @Test
    void testInsertAtInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(-1, 5));
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(1, 10));
    }

    @Test
    void testDeleteFirst() {
        list.insertLast(10);
        list.insertLast(20);

        var deleted = list.deleteFirst();

        assertEquals(1, list.size());
        assertEquals(10, deleted);
    }

    @Test
    void testDeleteLast() {
        list.insertLast(100);
        list.insertLast(200);

        var deleted = list.deleteLast();

        assertEquals(1, list.size());
        assertEquals(200, deleted);
    }

    @Test
    void testDeleteAtIndex() {
        list.insertLast(5);
        list.insertLast(10);
        list.insertLast(15);

        var deleted = list.deleteAt(1);

        assertEquals(2, list.size());
        assertEquals(10, deleted);
    }

    @Test
    void testDeleteAtInvalidIndex() {
        list.insertLast(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.deleteAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.deleteAt(1));
    }

    @Test
    void testDeleteFromEmptyList() {
        assertThrows(NoSuchElementException.class, list::deleteFirst);
        assertThrows(NoSuchElementException.class, list::deleteLast);
    }

    @Test
    void testIndexOfAndContains() {
        list.insertLast(42);
        list.insertLast(99);

        assertEquals(0, list.indexOf(42));
        assertEquals(1, list.indexOf(99));

        assertTrue(list.contains(42));
        assertFalse(list.contains(7));
    }

    @Test
    void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.insertLast(1);
        assertFalse(list.isEmpty());
        list.deleteFirst();
        assertTrue(list.isEmpty());
    }

}