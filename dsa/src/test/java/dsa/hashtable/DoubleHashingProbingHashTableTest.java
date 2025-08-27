package dsa.hashtable;

import static dsa.hashtable.OpenAddressingHashTable.ProbingStrategy.DOUBLE_HASHING;

class DoubleHashingProbingHashTableTest extends HashTableTest {

    @Override
    HashTable<String, Integer> newTable() {
        return new OpenAddressingHashTable<>(16, DOUBLE_HASHING);
    }

}