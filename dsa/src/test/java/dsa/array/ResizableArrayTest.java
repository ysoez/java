package dsa.array;

import dsa.list.ListTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResizableArrayTest extends ListTest {

    private final ResizableArray<Integer> array = new DynamicArray<>();

    @Nested
    class Set {
        @Test
        void testSetWhenArrayIsEmptyThrowsError() {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.set(0, 1));
        }
        @Test
        void testSetAndGet() {
            array.insertFirst(10);
            array.set(0, 20);
            assertEquals(20, array.get(0));
        }
    }

    @Nested
    class Get {
        @Test
        void testGetWhenArrayIsEmptyThrowsError() {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(0));
        }
        @Test
        void xx() {

        }
    }

}