package data_structure.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

interface ArrayTest {

    Array<Integer> create(Integer... value);

    Array<Integer> create(int capacity);

    @Test
    default void set() {
        var array = create(10, 20, 30);

        array.set(0, 1);
        array.set(1, 2);
        array.set(2, 3);

        assertEquals(1, array.get(0));
        assertEquals(2, array.get(1));
        assertEquals(3, array.get(2));
    }

    @Test
    default void setIfEmptyThrows() {
        var array = create(0);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.set(0, 100));
    }

    @Test
    default void setIfIndexIsOutOfBounds() {
        var array = create(10, 20, 30);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.set(-1, 100));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.set(3, 100));
    }

    @Test
    default void get() {
        var array = create(1, 2, 3);
        assertEquals(1, array.get(0));
        assertEquals(2, array.get(1));
        assertEquals(3, array.get(2));
    }

    @Test
    default void getIfEmptyThrows() {
        var array = create(0);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(0));
    }

    @Test
    default void getIfIndexIsOutOfBounds() {
        var array = create(10, 20, 30);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(3));
    }

    @Test
    default void length() {
        var array = new StaticArray<>(0);
        assertEquals(0, array.length());

        array = new StaticArray<>("10");
        assertEquals(1, array.length());

        array = new StaticArray<>(10, 20, 30);
        assertEquals(3, array.length());
    }

}
