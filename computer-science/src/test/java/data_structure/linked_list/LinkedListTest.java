package data_structure.linked_list;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

interface LinkedListTest {

    LinkedList<Integer> create();

    LinkedList<Integer> create(Integer... values);

    Collection<LinkedList<Integer>> dataSet();

    @Test
    default void addFirstWhenEmpty() {
        LinkedList<Integer> list = create();
        assertTrue(list.isEmpty());

        list.addFirst(Integer.MAX_VALUE);

        assertEquals(Integer.MAX_VALUE, list.getFirst());
        assertEquals(1, list.size());
    }

    @Test
    default void addFirst() {
        for (LinkedList<Integer> list : dataSet()) {
            int sizeBefore = list.size();
            list.addFirst(Integer.MAX_VALUE);
            assertEquals(Integer.MAX_VALUE, list.getFirst());
            assertEquals(sizeBefore + 1, list.size());
        }
    }

    @Test
    default void addLastWhenEmpty() {
        LinkedList<Integer> list = create();
        assertTrue(list.isEmpty());

        list.addLast(Integer.MAX_VALUE);

        assertEquals(Integer.MAX_VALUE, list.getLast());
        assertEquals(1, list.size());
    }

    @Test
    default void addLast() {
        for (LinkedList<Integer> list : dataSet()) {
            int sizeBefore = list.size();
            list.addLast(Integer.MAX_VALUE);
            assertEquals(Integer.MAX_VALUE, list.getLast());
            assertEquals(sizeBefore + 1, list.size());
        }
    }

    @Test
    default void getFirstWhenEmpty() {
        LinkedList<Integer> list = create();
        assertTrue(list.isEmpty());
        assertNull(list.getFirst());
    }

    @Test
    default void getFirst() {
        for (LinkedList<Integer> list : dataSet()) {
            list.addFirst(Integer.MAX_VALUE);
            assertEquals(Integer.MAX_VALUE, list.getFirst());
        }
    }

    @Test
    default void getLastWhenEmpty() {
        LinkedList<Integer> list = create();
        assertTrue(list.isEmpty());
        assertNull(list.getLast());
    }

    @Test
    default void getLast() {
        for (LinkedList<Integer> list : dataSet()) {
            list.addLast(Integer.MAX_VALUE);
            assertEquals(Integer.MAX_VALUE, list.getLast());
        }
    }

    @Test
    default void testMethods() {
        var list = create();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        assertFalse(list.contains(0));

        list.addFirst(10);
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
        assertTrue(list.contains(10));
        assertFalse(list.contains(1));

        list.addLast(20);
        assertEquals(2, list.size());
        assertFalse(list.isEmpty());
        assertTrue(list.contains(10));
        assertTrue(list.contains(20));
        assertFalse(list.contains(30));
    }

    @Test
    default void indexOf() {
        LinkedList<Integer> array = create();

        assertEquals(-1, array.indexOf(-1));
        assertEquals(-1, array.indexOf(0));
        assertEquals(-1, array.indexOf(1));

        array.addFirst(10);
        assertEquals(0, array.indexOf(10));
        assertEquals(-1, array.indexOf(20));

        array.addLast(20);
        assertEquals(0, array.indexOf(10));
        assertEquals(1, array.indexOf(20));
    }

    @Test
    default void removeFirstWhenEmpty() {
        var list = create();
        assertThrows(NoSuchElementException.class, list::removeFirst);
    }

    @Test
    default void removeFirstWhenSingle() {
        var list = create(10);

        assertEquals(10, list.removeFirst());

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    default void removeFirst() {
        var list = create(10, 20, 30);

        assertEquals(10, list.removeFirst());
        assertEquals(2, list.size());

        assertEquals(20, list.removeFirst());
        assertEquals(1, list.size());

        assertEquals(30, list.removeFirst());
        assertEquals(0, list.size());
    }

    @Test
    default void removeLastWhenEmpty() {
        var list = create();
        assertThrows(NoSuchElementException.class, list::removeLast);
    }

    @Test
    default void removeLastWhenSingle() {
        var list = create(10);
        assertEquals(10, list.removeLast());
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    default void removeLast() {
        var list = create(10, 20, 30);

        assertEquals(30, list.removeLast());
        assertEquals(2, list.size());

        assertEquals(20, list.removeLast());
        assertEquals(1, list.size());

        assertEquals(10, list.removeLast());
        assertEquals(0, list.size());
    }

    @Test
    default void toArray() {
        var list = create();
        assertArrayEquals(new Integer[0], list.toArray());

        list = create(10);
        assertArrayEquals(new Integer[]{10}, list.toArray());

        list = create(10, 20);
        assertArrayEquals(new Integer[]{10, 20}, list.toArray());

        list = create(10, 20, 30);
        assertArrayEquals(new Integer[]{10, 20, 30}, list.toArray());
    }

}
