package dsa.hashtable;

import dsa.Algorithm;
import dsa.Algorithm.Complexity;
import lombok.AllArgsConstructor;

import java.util.*;

import static dsa.Algorithm.Complexity.CONSTANT;
import static dsa.Algorithm.Complexity.LINEAR;

class ChainingHashTable<K, V> implements HashTable<K, V> {

    private final LinkedList<Entry<K, V>>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = LINEAR))
    ChainingHashTable(int capacity) {
        buckets = new LinkedList[capacity];
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public void put(K key, V value) {
        var entry = getEntry(key);
        if (entry != null) {
            entry.value = value;
            return;
        }
        getOrCreateBucket(key).add(new Entry<>(key, value));
        size++;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public V get(K key) {
        var entry = getEntry(key);
        return entry != null ? entry.value : null;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public int size() {
        return size;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public Set<K> keySet() {
        var keySet = new HashSet<K>();
        for (var bucket : buckets)
            if (bucket != null)
                for (var entry : bucket)
                    keySet.add(entry.key);
        return keySet;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = LINEAR, space = LINEAR))
    public Collection<V> values() {
        var values = new ArrayList<V>();
        for (var bucket : buckets)
            if (bucket != null)
                for (var entry : bucket)
                    values.add(entry.value);
        return values;
    }

    @Override
    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    public V remove(K key) {
        var entry = getEntry(key);
        if (entry == null)
            return null;
        getBucket(key).remove(entry);
        size--;
        return entry.value;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private Entry<K, V> getEntry(K key) {
        var bucket = getBucket(key);
        if (bucket != null)
            for (var entry : bucket)
                if (entry.key.equals(key))
                    return entry;
        return null;
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private LinkedList<Entry<K, V>> getBucket(K key) {
        return buckets[hash(key)];
    }

    @Algorithm(complexity = @Complexity(runtime = CONSTANT, space = CONSTANT))
    private LinkedList<Entry<K, V>> getOrCreateBucket(K key) {
        var index = hash(key);
        var bucket = buckets[index];
        if (bucket == null)
            bucket = buckets[index] = new LinkedList<>();
        return bucket;
    }

    @AllArgsConstructor
    private static class Entry<K, V> {
        private final K key;
        private V value;
    }

}
