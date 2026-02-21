package cache.lru;

import cache.Cache;

import java.util.Optional;

interface LruCache<K, V> extends Cache<K, V> {

    //
    // ~ Always places the entry at the most-recently used position and evicts LRU entry if size hits capacity
    //
    void put(K key, V value);

    //
    // ~ Promotes the entry to the MRU (most-recently used) position
    //
    Optional<V> get(K key);

}