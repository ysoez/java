package algorithm.caching;

import java.util.HashMap;
import java.util.Map;

class WriteBehindCache<K, V> {

    private Map<K, V> cache;
    private WriteBehindHandler<K, V> writeBehindHandler;

    public WriteBehindCache(WriteBehindHandler<K, V> writeBehindHandler) {
        this.cache = new HashMap<>();
        this.writeBehindHandler = writeBehindHandler;
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void put(K key, V value) {
        cache.put(key, value);
        submitWriteOperation(key, value);
    }

    public void remove(K key) {
        V value = cache.remove(key);
        submitDeleteOperation(key, value);
    }

    private void submitWriteOperation(K key, V value) {
        writeBehindHandler.writeAsync(key, value);
    }

    private void submitDeleteOperation(K key, V value) {
        writeBehindHandler.deleteAsync(key);
    }

    public interface WriteBehindHandler<K, V> {
        void writeAsync(K key, V value);

        void deleteAsync(K key);
    }
}

