package dsa.hashtable;

import static dsa.hashtable.OpenAddressingHashTable.ProbingStrategy.LINEAR;

class LinearProbingHashTableTest extends HashTableTest {

    @Override
    HashTable<String, Integer> newTable() {
        return new OpenAddressingHashTable<>(16, LINEAR);
    }

}