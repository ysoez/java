package dsa.array;

import dsa.list.List;
import dsa.list.ListTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

}