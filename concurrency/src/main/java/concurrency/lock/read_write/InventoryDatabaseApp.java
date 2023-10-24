package concurrency.lock.read_write;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static concurrency.ConcurrencyUtils.sleep;

class InventoryDatabaseApp {

    private static final int HIGHEST_PRICE = 1000;
    private final static Random RANDOM = new Random();

    public static void main(String[] args) throws InterruptedException {
        var database = new InventoryDatabase();
        for (int i = 0; i < 100000; i++) {
            database.addItem(RANDOM.nextInt(HIGHEST_PRICE));
        }

        var writer = new Thread(() -> {
            while (true) {
                database.addItem(RANDOM.nextInt(HIGHEST_PRICE));
                database.removeItem(RANDOM.nextInt(HIGHEST_PRICE));
                sleep(10);
            }
        });
        writer.setDaemon(true);
        writer.start();

        int numberOfReaderThreads = 7;
        List<Thread> readers = new ArrayList<>();
        for (int readerIndex = 0; readerIndex < numberOfReaderThreads; readerIndex++) {
            var reader = new Thread(() -> {
                for (int i = 0; i < 100_000; i++) {
                    int upperBoundPrice = RANDOM.nextInt(HIGHEST_PRICE);
                    int lowerBoundPrice = upperBoundPrice > 0 ? RANDOM.nextInt(upperBoundPrice) : 0;
                    database.getNumberOfItemsInPriceRange(lowerBoundPrice, upperBoundPrice);
                }
            });
            reader.setDaemon(true);
            readers.add(reader);
        }

        long startReadingTime = System.currentTimeMillis();
        for (Thread reader : readers) {
            reader.start();
        }
        for (Thread reader : readers) {
            reader.join();
        }
        long endReadingTime = System.currentTimeMillis();
        System.out.printf("Reading took: %d ms%n", endReadingTime - startReadingTime);
    }

    private static class InventoryDatabase {
        private final TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
        private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        private final Lock readLock = reentrantReadWriteLock.readLock();
        private final Lock writeLock = reentrantReadWriteLock.writeLock();
        private final Lock lock = new ReentrantLock();

        public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
//            lock.lock();
            readLock.lock();
            try {
                Integer fromKey = priceToCountMap.ceilingKey(lowerBound);
                Integer toKey = priceToCountMap.floorKey(upperBound);
                if (fromKey == null || toKey == null) {
                    return 0;
                }
                NavigableMap<Integer, Integer> rangeOfPrices = priceToCountMap.subMap(fromKey, true, toKey, true);
                int sum = 0;
                for (int numberOfItemsForPrice : rangeOfPrices.values()) {
                    sum += numberOfItemsForPrice;
                }
                return sum;
            } finally {
                readLock.unlock();
//                lock.unlock();
            }
        }

        public void addItem(int price) {
//            lock.lock();
            writeLock.lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if (numberOfItemsForPrice == null) {
                    priceToCountMap.put(price, 1);
                } else {
                    priceToCountMap.put(price, numberOfItemsForPrice + 1);
                }
            } finally {
                writeLock.unlock();
//                 lock.unlock();
            }
        }

        public void removeItem(int price) {
//            lock.lock();
            writeLock.lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
                    priceToCountMap.remove(price);
                } else {
                    priceToCountMap.put(price, numberOfItemsForPrice - 1);
                }
            } finally {
                writeLock.unlock();
//                 lock.unlock();
            }
        }
    }
}
