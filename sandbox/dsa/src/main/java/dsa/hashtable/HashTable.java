package dsa.hashtable;

import java.util.Collection;
import java.util.Set;

interface HashTable<K, V> {

    void put(K key, V value);

    V get(K key);

    int size();

    boolean isEmpty();

    Set<K> keySet();

    Collection<V> values();

    V remove(K key);

}
