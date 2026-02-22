package cache.lru;

import java.util.LinkedHashMap;
import java.util.Optional;

class InsertionOrderLinkedHashMapLruCache<K, V> implements LruCache<K, V> {

    private final int capacity;
    private final LinkedHashMap<K, V> store;

    public InsertionOrderLinkedHashMapLruCache(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("capacity must be positive");
        this.capacity = capacity;
        //
        // ~ insertion-ordered by default
        //
        this.store = new LinkedHashMap<>();
    }

    @Override
    public synchronized void put(K key, V value) {
        //
        // ~ remove first so re-insertion always lands at the tail (MRU end)
        //
        store.remove(key);
        if (store.size() == capacity) {
            evictLRU();
        }
        //
        // ~ tail = MRU
        //
        store.put(key, value);
    }

    @Override
    public synchronized Optional<V> get(K key) {
        if (!store.containsKey(key))
            return Optional.empty();
        //
        // ~ promote to MRU: remove + re-insert moves it to the tail
        //
        V value = store.remove(key);
        store.put(key, value);
        return Optional.of(value);
    }

    @Override
    public synchronized void remove(K key) {
        store.remove(key);
    }

    @Override
    public synchronized void clear() {
        store.clear();
    }

    @Override
    public synchronized int size() {
        return store.size();
    }

    @Override
    public int capacity() {
        //
        // ~ immutable
        //
        return capacity;
    }

    private void evictLRU() {
        //
        // ~ head of an insertion-order map = entry that was reinserted earliest (LRU)
        //
        K lruKey = store.keySet().iterator().next();
        //
        // ~ evict LRU
        //
        store.remove(lruKey);
    }

}
