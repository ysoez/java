package data_structure.array;

import org.junit.jupiter.api.Test;

import static data_structure.array.DynamicArray.DEFAULT_CAPACITY;
import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest implements ArrayTest {

    @Override
    public Array<Integer> create(Integer... value) {
        return new DynamicArray<>(value);
    }

    @Override
    public Array<Integer> create(int capacity) {
        return new DynamicArray<>(capacity);
    }

    @Test
    void insertFirst() {
        var array = new DynamicArray<>();

        array.insertFirst(30);
        assertEquals(30, array.get(0));
        assertEquals(1, array.size());

        array.insertFirst(20);
        assertEquals(20, array.get(0));
        assertEquals(2, array.size());

        array.insertFirst(10);
        assertEquals(10, array.get(0));
        assertEquals(3, array.size());
    }

    @Test
    void insertFirstWithResize() {
        var array = new DynamicArray<>(10, 20, 30);
        int initialLength = array.length();
        assertEquals(array.size(), initialLength);

        array.insertFirst(1);

        assertEquals(1, array.get(0));
        assertEquals(4, array.size());
        assertTrue(array.length() > initialLength);
    }

    @Test
    void insertAtOutOfBounds() {
        var array = new DynamicArray<>(0);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.insertAt(-1, 1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.insertAt(0, 1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.insertAt(1, 1));
    }

    @Test
    void insertAtWithResize() {
        var array = new DynamicArray<>(10, 20, 30);
        int initialLength = array.length();
        assertEquals(array.size(), initialLength);

        array.insertAt(0, 1);

        assertEquals(1, array.get(0));
        assertEquals(4, array.size());
        assertTrue(array.length() > initialLength);
    }

    @Test
    void insertAt() {
        var array = new DynamicArray<>("C");

        array.insertAt(0, "A");
        assertEquals("A", array.get(0));
        assertEquals(2, array.size());

        array.insertAt(1, "B");
        assertEquals("B", array.get(1));
        assertEquals(3, array.size());
    }

    @Test
    void insertLastWithResize() {
        var array = new DynamicArray<>(10, 20, 30);
        int initialLength = array.length();
        assertEquals(array.size(), initialLength);

        array.insertLast(40);

        assertEquals(40, array.get(3));
        assertEquals(4, array.size());
        assertTrue(array.length() > initialLength);
    }

    @Test
    void insertLast() {
        var array = new DynamicArray<>();

        array.insertLast(10);
        assertEquals(10, array.get(0));
        assertEquals(1, array.size());

        array.insertLast(20);
        assertEquals(20, array.get(1));
        assertEquals(2, array.size());

        array.insertLast(30);
        assertEquals(30, array.get(2));
        assertEquals(3, array.size());
    }

    @Test
    void sizeAndIsEmpty() {
        var array = new DynamicArray<>();
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());

        array.insertFirst(10);
        assertEquals(1, array.size());
        assertFalse(array.isEmpty());

        array.insertLast(20);
        assertEquals(2, array.size());
        assertFalse(array.isEmpty());

        array.insertAt(1, 15);
        assertEquals(3, array.size());
        assertFalse(array.isEmpty());

        array.deleteFirst();
        assertEquals(2, array.size());
        assertFalse(array.isEmpty());

        array.deleteLast();
        assertEquals(1, array.size());
        assertFalse(array.isEmpty());

        array.deleteAt(0);
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
    }

    @Test
    void indexOf() {
        var array = new DynamicArray<>();

        assertEquals(-1, array.indexOf(-1));
        assertEquals(-1, array.indexOf(0));
        assertEquals(-1, array.indexOf(1));

        array.insertFirst(10);
        assertEquals(0, array.indexOf(10));
        assertEquals(-1, array.indexOf(20));

        array.insertLast(20);
        assertEquals(0, array.indexOf(10));
        assertEquals(1, array.indexOf(20));
    }

    @Test
    void trimToSize() {
        var array = new DynamicArray<>();
        assertEquals(DEFAULT_CAPACITY, array.length());

        array.insertFirst(10);
        assertEquals(DEFAULT_CAPACITY, array.length());

        array.trimToSize();
        assertEquals(array.size(), array.length());
    }

    @Test
    void deleteFirst() {
        var array = new DynamicArray<Integer>();
        assertThrows(IllegalStateException.class, array::deleteFirst);

        array = new DynamicArray<>();
        array.insertFirst(10);
        assertEquals(10, array.deleteFirst());
        assertTrue(array.isEmpty());

        array = new DynamicArray<>(10, 20);
        assertEquals(10, array.deleteFirst());
        assertEquals(20, array.deleteFirst());

        array = new DynamicArray<>(10, 20, 30);
        assertEquals(10, array.deleteFirst());
        assertEquals(20, array.deleteFirst());
        assertEquals(30, array.deleteFirst());
    }

    @Test
    void deleteAt() {
        var emptyArray = new DynamicArray<>();
        assertThrows(IllegalStateException.class, () -> emptyArray.deleteAt(0));

        var array = new DynamicArray<>();
        array.insertFirst(10);
        assertEquals(10, array.deleteAt(0));

        array = new DynamicArray<>(10, 20);
        assertEquals(10, array.deleteAt(0));
        assertEquals(20, array.deleteAt(0));

        array = new DynamicArray<>(10, 20);
        assertEquals(20, array.deleteAt(1));
        assertEquals(10, array.deleteAt(0));

        array = new DynamicArray<>(10, 20, 30);
        assertEquals(10, array.deleteAt(0));
        assertEquals(20, array.deleteAt(0));
        assertEquals(30, array.deleteAt(0));

        array = new DynamicArray<>(10, 20, 30);
        assertEquals(30, array.deleteAt(2));
        assertEquals(20, array.deleteAt(1));
        assertEquals(10, array.deleteAt(0));
    }

    @Test
    void deleteLast() {
        var emptyArray = new DynamicArray<>();
        assertThrows(IllegalStateException.class, emptyArray::deleteLast);

        var array = new DynamicArray<>();
        array.insertFirst(10);
        assertEquals(10, array.deleteLast());
        assertTrue(array.isEmpty());

        array = new DynamicArray<>(10, 20);
        assertEquals(20, array.deleteLast());
        assertEquals(10, array.deleteLast());
        assertTrue(array.isEmpty());

        array = new DynamicArray<>(10, 20, 30);
        assertEquals(30, array.deleteLast());
        assertEquals(20, array.deleteLast());
        assertEquals(10, array.deleteLast());
        assertTrue(array.isEmpty());
    }

    @Test
    void arrayToString() {
        var array = new DynamicArray<>();
        assertEquals("[]", array.toString());

        array.insertFirst(10);
        assertEquals("[10]", array.toString());

        array.insertLast(20);
        assertEquals("[10, 20]", array.toString());

        array.insertAt(1, 15);
        assertEquals("[10, 15, 20]", array.toString());
    }

}