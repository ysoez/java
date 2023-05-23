package concurrency.collection;

import java.util.ArrayList;
import java.util.List;

class ConcurrentHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private List<List<Entry<K, V>>> buckets;

    public ConcurrentHashMap() {
        buckets = new ArrayList<>(DEFAULT_CAPACITY);
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            buckets.add(new ArrayList<>());
        }
    }

    public void put(K key, V value) {
        List<Entry<K, V>> bucket = getBucket(key);;
        synchronized (bucket) {
            for (Entry<K, V> entry : bucket) {
                if (entry.getKey().equals(key)) {
                    entry.setValue(value);
                    return;
                }
            }

            bucket.add(new Entry<>(key, value));
        }
    }

    public V get(K key) {
        List<Entry<K, V>> bucket = getBucket(key);;
        synchronized (bucket) {
            for (Entry<K, V> entry : bucket) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public void remove(K key) {
        List<Entry<K, V>> bucket = getBucket(key);;
        synchronized (bucket) {
            for (Entry<K, V> entry : bucket) {
                if (entry.getKey().equals(key)) {
                    bucket.remove(entry);
                    return;
                }
            }
        }
    }

    private List<Entry<K, V>> getBucket(K key) {
        int bucketIndex = getBucketIndex(key);
        return buckets.get(bucketIndex);
    }

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode()) % DEFAULT_CAPACITY;
    }

    private static class Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}

