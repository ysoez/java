package data_structure.hashtable;

import lombok.AllArgsConstructor;

import java.util.LinkedList;

class ChainngHashTable<K, V> implements HashTable<K, V> {

    @SuppressWarnings("unchecked")
    private final LinkedList<Entry<K, V>>[] entries = new LinkedList[10];

    @Override
    public void put(K key, V value) {
        var entry = getEntry(key);
        if (entry != null) {
            entry.value = value;
            return;
        }
        getOrCreateBucket(key).add(new Entry<>(key, value));
    }

    @Override
    public V get(K key) {
        var entry = getEntry(key);
        return entry != null ? entry.value : null;
    }

    @Override
    public void remove(K key) {
        var entry = getEntry(key);
        if (entry == null)
            return;
        getBucket(key).remove(entry);
    }

    private Entry<K, V> getEntry(K key) {
        var bucket = getBucket(key);
        if (bucket != null) {
            for (var entry : bucket)
                if (entry.key.equals(key))
                    return entry;
        }
        return null;
    }

    private LinkedList<Entry<K, V>> getBucket(K key) {
        return entries[hash(key)];
    }

    private LinkedList<Entry<K, V>> getOrCreateBucket(K key) {
        int index = hash(key);
        var bucket = entries[index];
        if (bucket == null)
            entries[index] = bucket = new LinkedList<>();
        return bucket;
    }

    private int hash(K key) {
        return Math.absExact(key.hashCode()) % entries.length;
    }

    @AllArgsConstructor
    private static class Entry<K, V> implements HashTable.Entry<K, V> {

        private final K key;
        private V value;

        @Override
        public K key() {
            return key;
        }

        @Override
        public V value() {
            return value;
        }
    }

}
