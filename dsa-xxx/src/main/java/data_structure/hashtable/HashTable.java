package data_structure.hashtable;

interface HashTable<K, V> {

    void put(K key, V value);

    V get(K key);

    void remove(K key);

    interface Entry<K, V> {
        K key();
        V value();
    }

}
