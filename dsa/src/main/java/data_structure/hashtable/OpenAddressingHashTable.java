package data_structure.hashtable;

class OpenAddressingHashTable<K, V> implements HashTable<K, V> {

    @SuppressWarnings("unchecked")
    private final Entry<K, V>[] entries = new Entry[10];
    private final ProbingStrategy probingStrategy;

    OpenAddressingHashTable(ProbingStrategy probingStrategy) {
        this.probingStrategy = probingStrategy;
    }

    @Override
    public void put(K key, V value) {
        int attempt = 0;
        int index = nextSlot(key, attempt);
        while (entries[index] != null && !entries[index].isDeleted && entries[index].key != key)
            index = nextSlot(key, ++attempt);
        entries[index] = new Entry<>(key, value);
    }

    @Override
    public V get(K key) {
        int attempt = 0;
        int index = nextSlot(key, attempt);
        int start = index;
        while (entries[index] != null) {
            if (!entries[index].isDeleted && entries[index].key == key) {
                return entries[index].value;
            }
            attempt++;
            index = nextSlot(key, attempt);
            if (index == start)
                break;
        }
        return null;
    }

    @Override
    public void remove(K key) {
        int attempt = 0;
        int index = nextSlot(key, attempt);
        int start = index;
        while (entries[index] != null) {
            if (!entries[index].isDeleted && entries[index].key == key) {
                entries[index].isDeleted = true;
                return;
            }
            attempt++;
            index = nextSlot(key, attempt);
            if (index == start)
                break;
        }
    }

    private int nextSlot(K key, int attempt) {
        return (hash(key) + probingStrategy.probe(attempt, tableSize())) % tableSize();
    }

    private int hash(K key) {
        return Math.absExact(key.hashCode()) % entries.length;
    }

    private int tableSize() {
        return entries.length;
    }

    private static class Entry<K, V> implements HashTable.Entry<K, V> {
        private final K key;
        private V value;
        private boolean isDeleted;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.isDeleted = false;
        }

        @Override
        public K key() {
            return key;
        }

        @Override
        public V value() {
            return value;
        }
    }

    interface ProbingStrategy {
        int probe(int attempt, int tableSize);
    }

    static class LinearProbing implements ProbingStrategy {
        public int probe(int attempt, int tableSize) {
            return attempt % tableSize;
        }
    }

    static class QuadraticProbing implements ProbingStrategy {
        public int probe(int attempt, int tableSize) {
            return (attempt * attempt) % tableSize;
        }
    }

    static class DoubleHashing implements ProbingStrategy {
        public int probe(int attempt, int tableSize) {
            return attempt * secondaryHash(attempt, tableSize) % tableSize;
        }
        private int secondaryHash(int key, int tableSize) {
            int prime = tableSize - 1;
            return prime - (key % prime);
        }
    }

}
