package dsa.array;

import dsa.list.List;
import dsa.list.ListTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResizableArrayTest extends ListTest {

    @Override
    public List<Integer> newList() {
        return new DynamicArray<>();
    }

    @Test
    void testInsertLastAndGrow() {
        var array = new DynamicArray<>(3);
        array.insertLast(10);
        array.insertLast(20);
        array.insertLast(30);
        assertEquals(3, array.length());

        array.insertLast(40);

        assertEquals(40, array.get(3));
        assertEquals(4, array.size());
        assertEquals(6, array.length());
    }

    @Test
    void testInsertAtAndGrow() {
        var array = new DynamicArray<>(3);
        array.insertLast(10);
        array.insertLast(20);
        array.insertLast(30);
        assertEquals(3, array.length());

        array.insertAt(2, 25);

        assertEquals(25, array.get(2));
        assertEquals(4, array.size());
        assertEquals(6, array.length());
    }

    @Test
    void testTrimToSize() {
        var array = new DynamicArray<>(5);
        array.insertLast(1);

        array.trimToSize();

        assertEquals(array.size(), array.length());
    }

}