package dsa.hashtable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChainingHashTableTest extends HashTableTest {

    @Override
    HashTable<String, Integer> newTable() {
        return new ChainingHashTable<>(3);
    }

    @Test
    void testHandlingCollisions() {
        var table = new ChainingHashTable<Integer, String>(2);

        table.put(6, "A");  // 1
        table.put(8, "B");  // 3
        table.put(11, "C"); // 1

        assertEquals("A", table.get(6));
        assertEquals("B", table.get(8));
        assertEquals("C", table.get(11));
    }

}