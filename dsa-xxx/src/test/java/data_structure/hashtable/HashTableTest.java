package data_structure.hashtable;

import data_structure.hashtable.OpenAddressingHashTable.DoubleHashing;
import data_structure.hashtable.OpenAddressingHashTable.LinearProbing;
import data_structure.hashtable.OpenAddressingHashTable.QuadraticProbing;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class HashTableTest {

    @ParameterizedTest
    @MethodSource("implementations")
    void testPutAndGet(HashTable<Integer, String> hashTable) {
        hashTable.put(1, "A");
        assertEquals("A", hashTable.get(1));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testPutOverride(HashTable<Integer, String> hashTable) {
        hashTable.put(1, "A");
        hashTable.put(1, "AA");
        assertEquals("AA", hashTable.get(1));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testGetNonExistentKey(HashTable<Integer, String> hashTable) {
        assertThrows(ArithmeticException.class, () -> hashTable.get(Integer.MIN_VALUE));
        assertNull(hashTable.get(Integer.MIN_VALUE + 1));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testRemove(HashTable<Integer, String> hashTable) {
        hashTable.put(1, "A");
        hashTable.remove(1);
        assertNull(hashTable.get(1));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testRemoveNonExistentKey(HashTable<Integer, String> hashTable) {
        assertThrows(ArithmeticException.class, () -> hashTable.remove(Integer.MIN_VALUE));
        assertDoesNotThrow(() -> hashTable.remove(Integer.MIN_VALUE + 1));
    }

    private static Stream<Arguments> implementations() {
        return Stream.of(
                arguments(new ChainngHashTable<Integer, String>()),
                arguments(new OpenAddressingHashTable<>(new LinearProbing())),
                arguments(new OpenAddressingHashTable<>(new QuadraticProbing())),
                arguments(new OpenAddressingHashTable<>(new DoubleHashing()))
        );
    }

}