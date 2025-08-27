package dsa.hashtable;

import org.junit.jupiter.api.Test;

import static dsa.hashtable.OpenAddressingHashTable.ProbingStrategy.LINEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LinearProbingHashTableTest extends HashTableTest {

    @Override
    HashTable<String, Integer> newTable() {
        return new OpenAddressingHashTable<>(16, LINEAR);
    }

    @Test
    void testHandlingCollisions() {
        var table = new OpenAddressingHashTable<>(3, LINEAR);

        table.put(3, "A"); // 3 % 3 = 0
        table.put(6, "B"); // 6 % 3 = 0 (collision, linear probe)

        assertEquals("A", table.get(3));
        assertEquals("B", table.get(6));
    }

    @Test
    void testRemoveAndReuseSlot() {
        var table = new OpenAddressingHashTable<>(3, LINEAR);

        table.put(3, "A");
        table.put(6, "B");

        assertEquals("A", table.remove(3));
        table.put(9, "C"); // should reuse deleted slot

        assertEquals("C", table.get(9));
        assertEquals("B", table.get(6));
    }

}