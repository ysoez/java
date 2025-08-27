package dsa.hashtable;

import java.util.*;

class OpenAddressingHashTable<K, V> implements HashTable<K, V> {
    private Entry<K, V>[] table;
    private int size;
    private final ProbingStrategy probingStrategy;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    @SuppressWarnings("unchecked")
    OpenAddressingHashTable(int initialCapacity, ProbingStrategy strategy) {
        this.table = (Entry<K, V>[]) new Entry[initialCapacity];
        this.size = 0;
        this.probingStrategy = strategy;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if ((double) (size + 1) / table.length > LOAD_FACTOR_THRESHOLD) {
            resize();
        }

        int index = findSlot(key);
        if (table[index] == null || table[index].isDeleted) {
            table[index] = new Entry<>(key, value);
            size++;
        } else if (table[index].key.equals(key)) {
            table[index].value = value;
        }
    }

    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        int index = findSlot(key);
        if (table[index] != null && !table[index].isDeleted && table[index].key.equals(key)) {
            return table[index].value;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        int index = findSlot(key);
        if (table[index] != null && !table[index].isDeleted && table[index].key.equals(key)) {
            V value = table[index].value;
            table[index].isDeleted = true;
            size--;
            return value;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Entry<K, V> entry : table) {
            if (entry != null && !entry.isDeleted) {
                keys.add(entry.key);
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            if (entry != null && !entry.isDeleted) {
                values.add(entry.value);
            }
        }
        return values;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    private int hash2(K key) {
        return 1 + (Math.abs(key.hashCode()) % (table.length - 1));
    }

    private int findSlot(K key) {
        int hash = hash(key);
        int i = 0;

        switch (probingStrategy) {
            case LINEAR:
                while (table[hash] != null && !table[hash].isDeleted && !table[hash].key.equals(key)) {
                    hash = (hash + 1) % table.length;
                }
                break;
            case QUADRATIC:
                while (table[hash] != null && !table[hash].isDeleted && !table[hash].key.equals(key)) {
                    i++;
                    hash = (hash(key) + i * i) % table.length;
                }
                break;
            case DOUBLE_HASHING:
                int step = hash2(key);
                while (table[hash] != null && !table[hash].isDeleted && !table[hash].key.equals(key)) {
                    i++;
                    hash = (hash(key) + i * step) % table.length;
                }
                break;
        }
        return hash;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = (Entry<K, V>[]) new Entry[oldTable.length * 2];
        size = 0;

        for (Entry<K, V> entry : oldTable) {
            if (entry != null && !entry.isDeleted) {
                put(entry.key, entry.value);
            }
        }
    }

    enum ProbingStrategy {
        LINEAR, QUADRATIC, DOUBLE_HASHING
    }

    private static class Entry<K, V> {
        K key;
        V value;
        boolean isDeleted;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.isDeleted = false;
        }
    }

}
