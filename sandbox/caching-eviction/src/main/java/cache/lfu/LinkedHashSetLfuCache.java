package cache.lfu;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;

public class LinkedHashSetLfuCache<K, V> implements LfuCache<K, V> {

    private final int capacity;
    private int minFreq;

    private final Map<K, V> keyToValue = new HashMap<>();
    private final Map<K, Integer> keyToFreq = new HashMap<>();
    private final Map<Integer, LinkedHashSet<K>> freqToKeys = new HashMap<>();

    public LinkedHashSetLfuCache(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("capacity must be positive");
        this.capacity = capacity;
    }

    @Override
    public synchronized void put(K key, V value) {
        if (keyToValue.containsKey(key)) {
            //
            // ~ update value and bump frequency
            //
            keyToValue.put(key, value);
            incrementFrequency(key);
        } else {
            if (keyToValue.size() == capacity) {
                evict();
            }
            keyToValue.put(key, value);
            keyToFreq.put(key, 1);
            freqToKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
            minFreq = 1;
        }
    }

    @Override
    public synchronized Optional<V> get(K key) {
        if (!keyToValue.containsKey(key))
            return Optional.empty();
        incrementFrequency(key);
        return Optional.of(keyToValue.get(key));
    }

    @Override
    public synchronized void remove(K key) {
        if (!keyToValue.containsKey(key))
            return;
        int freq = keyToFreq.remove(key);
        keyToValue.remove(key);
        freqToKeys.get(freq).remove(key);
    }

    @Override
    public synchronized void clear() {
        keyToValue.clear();
        keyToFreq.clear();
        freqToKeys.clear();
        minFreq = 0;
    }

    @Override
    public synchronized int size() {
        return keyToValue.size();
    }

    @Override
    public int capacity() {
        return capacity;
    }

    private void incrementFrequency(K key) {
        int freq = keyToFreq.get(key);
        keyToFreq.put(key, freq + 1);

        LinkedHashSet<K> oldBucket = freqToKeys.get(freq);
        oldBucket.remove(key);
        if (oldBucket.isEmpty() && freq == minFreq) {
            minFreq++;
        }

        freqToKeys.computeIfAbsent(freq + 1, _ -> new LinkedHashSet<>()).add(key);
    }

    private void evict() {
        LinkedHashSet<K> minBucket = freqToKeys.get(minFreq);
        //
        // ~ oldest among lowest-freq keys
        //
        K evictKey = minBucket.iterator().next();
        //
        // ~ evict
        //
        minBucket.remove(evictKey);
        keyToValue.remove(evictKey);
        keyToFreq.remove(evictKey);
    }

}