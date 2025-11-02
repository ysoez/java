package multithreading.atomicity;

import java.util.concurrent.atomic.AtomicInteger;

public class Counters {

    private static final Counter UNSAFE = new UnsafeCounter();
    private static final Counter SYNCHRONIZED = new SynchronizedCounter();
    private static final Counter ATOMIC = new AtomicCounter();

    public static void main(String[] args) throws InterruptedException {
        var counter = UNSAFE;

        var t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter.increment();
            }
        });
        var t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                counter.increment();
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(counter);
    }

    interface Counter {
        long value();
        void increment();
    }

    private static class UnsafeCounter implements Counter {

        private int count;

        @Override
        public long value() {
            return count;
        }

        @Override
        public void increment() {
            count++;
        }

        @Override
        public String toString() {
            return String.valueOf(count);
        }
    }

    private static class SynchronizedCounter implements Counter {

        private int count;

        @Override
        public synchronized long value() {
            return count;
        }

        @Override
        public synchronized void increment() {
            count++;
        }

        @Override
        public String toString() {
            return String.valueOf(count);
        }
    }

    private static class AtomicCounter implements Counter {

        private final AtomicInteger count = new AtomicInteger(0);

        @Override
        public synchronized long value() {
            return count.get();
        }

        @Override
        public synchronized void increment() {
            count.incrementAndGet();
        }

        @Override
        public String toString() {
            return String.valueOf(count);
        }
    }

}
