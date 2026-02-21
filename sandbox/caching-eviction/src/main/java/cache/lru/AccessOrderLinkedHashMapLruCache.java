package cache.lru;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

class AccessOrderLinkedHashMapLruCache<K, V> implements LruCache<K, V> {

    private final int capacity;
    private final LinkedHashMap<K, V> store;

    AccessOrderLinkedHashMapLruCache(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("capacity must be positive");
        this.capacity = capacity;
        this.store = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public synchronized void put(K key, V value) {
        store.put(key, value);
    }

    @Override
    public synchronized Optional<V> get(K key) {
        //
        // ~ get() on an access-order map moves the entry to the tail (MRU position)
        //
        return Optional.ofNullable(store.get(key));
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

}
