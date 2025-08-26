package dsa.hashtable;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

abstract class HashTableTest {

    abstract HashTable<String, Integer> newTable();

    @Test
    void testEmptyTable() {
        var table = newTable();
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
    }

    @Test
    void testSizeAndIsEmpty() {
        var table = newTable();
        assertTrue(table.isEmpty());

        table.put("Alice", 10);
        table.put("Bob", 20);

        assertEquals(2, table.size());
        assertFalse(table.isEmpty());

        table.remove("Bob");
        assertEquals(1, table.size());
    }

    @Test
    void testPutAndGet() {
        var table = newTable();
        table.put("Alice", 10);
        table.put("Bob", 20);

        assertEquals(10, table.get("Alice"));
        assertEquals(20, table.get("Bob"));
        assertNull(table.get("NonExistent"));
    }

    @Test
    void testDuplicateKeyOverwriteValue() {
        var table = newTable();
        table.put("A", 1);
        table.put("A", 2);

        assertEquals(2, table.get("A"));
        assertEquals(1, table.size());
    }

    @Test
    void testKeySet() {
        var table = newTable();
        assertTrue(table.keySet().isEmpty());

        table.put("A", 1);
        table.put("B", 2);
        assertEquals(Set.of("A", "B"), table.keySet());
    }

    @Test
    void testValues() {
        var table = newTable();
        assertTrue(table.values().isEmpty());

        table.put("A", 1);
        table.put("B", 2);
        table.put("C", 2);
        assertEquals(List.of(1, 2, 2), table.values());
    }

    @Test
    void testRemove() {
        var table = newTable();
        table.put("Alice", 10);
        table.put("Bob", 20);

        assertEquals(10, table.remove("Alice"));
        assertNull(table.get("Alice"));
        assertEquals(1, table.size());

        assertNull(table.remove("NonExistent"));
    }

    //    @Test
//    void testHandlingCollisions() {
//        // Force collisions by using keys with same hash index
//        HashTable<Integer, String> intTable = new LinearProbingHashTable<>(3);
//
//        intTable.put(3, "A"); // 3 % 3 = 0
//        intTable.put(6, "B"); // 6 % 3 = 0 (collision, linear probe)
//
//        assertEquals("A", intTable.get(3));
//        assertEquals("B", intTable.get(6));
//    }

//
//    @Test
//    void testRemoveAndReuseSlot() {
//        HashTable<Integer, String> intTable = new LinearProbingHashTable<>(3);
//
//        intTable.put(3, "A");
//        intTable.put(6, "B");
//
//        assertEquals("A", intTable.remove(3));
//        intTable.put(9, "C"); // should reuse deleted slot
//
//        assertEquals("C", intTable.get(9));
//        assertEquals("B", intTable.get(6));
//    }


}