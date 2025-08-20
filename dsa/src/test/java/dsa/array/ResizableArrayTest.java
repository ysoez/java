package dsa.array;

import dsa.list.ListTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// list tests may be not relevant (insert last & grow for example)
// !!! in list tests just cover the contract only !!!
class ResizableArrayTest /*extends ListTest*/ {

//    private final ResizableArray<Integer> array = new DynamicArray<>();

    @Test
    void testInsertLastAndGrow() {
        var array = new DynamicArray<>(3);

        array.insertLast(10);
        array.insertLast(20);
        array.insertLast(30);
        assertEquals(3, array.length());

        array.insertLast(40);
        assertEquals(6, array.length());
    }

    @Test
    void testDeleteAt() {
        var array = new DynamicArray<>();
        array.insertLast(10);
        array.insertLast(20);
        array.insertLast(30);
        array.insertLast(40);

        assertEquals(10, array.deleteAt(0));;
        assertEquals(20, array.get(0));

        assertEquals(30, array.deleteAt(1));
        assertEquals(20, array.get(0));
        assertEquals(40, array.get(1));

        assertEquals(20, array.deleteAt(0));
        assertEquals(40, array.get(0));

        assertEquals(40, array.deleteAt(0));
        assertTrue(array.isEmpty());
    }



//    @Nested
//    class Set {
//        @Test
//        void testSetWhenArrayIsEmptyThrowsError() {
//            assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.set(0, 1));
//        }
//        @Test
//        void testSetAndGet() {
//            array.insertFirst(10);
//            array.set(0, 20);
//            assertEquals(20, array.get(0));
//        }
//    }
//
//    @Nested
//    class Get {
//        @Test
//        void testGetWhenArrayIsEmptyThrowsError() {
//            assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.get(0));
//        }
//        @Test
//        void xx() {
//
//        }
//    }

}