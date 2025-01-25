package library.concurrency.synchronizer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static library.concurrency.ConcurrencyUtils.*;

class SemaphoreResourcePool {

    public static void main(String[] args) {
        var pool = Pool.of(new Integer[]{1, 2, 3, 4, 5, 6});
        var threadsCount = 6;
        var latch = new CountDownLatch(threadsCount);
        for (int i = 1; i <= threadsCount; i++) {
            new Thread(() -> {
                latch.countDown();
                try {
                    latch.await();
                    logRed("try acquire resource");
                    var resource = pool.getItem();
                    logGreen("acquired resource: " + resource);
                    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(3));
                    pool.putItem(resource);
                    logYellow("release resource: " + resource);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "worker-" + i).start();
        }
    }

    private static class Pool {
        private static final int MAX_AVAILABLE = 3;
        private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
        private final boolean[] used = new boolean[MAX_AVAILABLE];
        private Object[] resources;

        public static Pool of(Object[] resources) {
            var pool = new Pool();
            pool.resources = resources;
            return pool;
        }

        public Object getItem() throws InterruptedException {
            available.acquire();
            return getNextAvailableItem();
        }

        public void putItem(Object item) {
            if (markAsUnused(item)) {
                available.release();
            }
        }

        private synchronized Object getNextAvailableItem() {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                if (!used[i]) {
                    used[i] = true;
                    return resources[i];
                }
            }
            return null; // ~ not reached
        }

        private synchronized boolean markAsUnused(Object item) {
            for (int i = 0; i < MAX_AVAILABLE; ++i) {
                if (item == resources[i]) {
                    if (used[i]) {
                        used[i] = false;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }
    }
}
