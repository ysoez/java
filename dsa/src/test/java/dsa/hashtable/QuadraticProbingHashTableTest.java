package dsa.hashtable;

import static dsa.hashtable.OpenAddressingHashTable.ProbingStrategy.QUADRATIC;

class QuadraticProbingHashTableTest extends HashTableTest {

    @Override
    HashTable<String, Integer> newTable() {
        return new OpenAddressingHashTable<>(16, QUADRATIC);
    }

}