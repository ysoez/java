package cache.lfu;

import cache.Cache;

import java.util.Optional;

interface LfuCache<K, V> extends Cache<K, V> {

    //
    // ~ Preserves key frequency on value updates
    //
    void put(K key, V value);

    //
    // ~ Increments the entry's frequency counter
    //
    Optional<V> get(K key);

}