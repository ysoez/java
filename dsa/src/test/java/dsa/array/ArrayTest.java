package dsa.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTest {

    private final Array<String> array = new StaticArray<>();

    @Test
    void testGetBeforeSetReturnsNull() {
        assertNull(array.get(0));
        assertNull(array.get(1));
    }

    @Test
    void testSetAndGet() {
        array.set(0, "apple");
        assertEquals("apple", array.get(0));

        array.set(4, "banana");
        assertEquals("banana", array.get(4));
    }

    @Test
    void testGetInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(array.length()));
    }

    @Test
    void testSetInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(-1, "a"));
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(array.length(), "b"));
    }

    @Test
    void testLength() {
        assertEquals(StaticArray.DEFAULT_CAPACITY, array.length());
        assertEquals(10, new StaticArray<>(10).length());
        assertEquals(3, new StaticArray<>("a", "b", "c").length());

    }

    @Test
    void testSetOverwriteValue() {
        array.set(1, "100");
        array.set(1, "200");

        assertEquals("200", array.get(1));
    }

}