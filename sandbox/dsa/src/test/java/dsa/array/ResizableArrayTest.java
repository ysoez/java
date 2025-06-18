package dsa.array;

import org.junit.jupiter.api.Test;

import static dsa.array.DynamicArray.DEFAULT_CAPACITY;
import static org.junit.jupiter.api.Assertions.*;

class ResizableArrayTest {

    @Test
    void testDefaultInstantiation() {
        var array = new DynamicArray<>();
        assertEquals(0, array.size());
        assertEquals(DEFAULT_CAPACITY, array.length());
    }

    @Test
    void testInstantiationWithCapacity() {
        var array = new DynamicArray<>(20);
        assertEquals(0, array.size());
        assertEquals(20, array.length());
    }

    @Test
    void testInstantiationWithInitializer() {
        var array = new DynamicArray<>(1, 2, 3);
        assertEquals(3, array.size());
        assertEquals(3, array.length());
    }

    @Test
    void testInsertFirst() {
        var array = new DynamicArray<>();
        array.insertFirst(2);
        array.insertFirst(1);
        assertEquals(2, array.size());
        assertEquals(1, array.get(0));
        assertEquals(2, array.get(1));
    }

    @Test
    void testInsertFirstWithResize() {
        var array = new DynamicArray<>();
        array.insertFirst(2);
        array.insertFirst(1);
        assertEquals(2, array.size());
        assertEquals(1, array.get(0));
        assertEquals(2, array.get(1));
    }

}