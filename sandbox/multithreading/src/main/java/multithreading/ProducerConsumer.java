package multithreading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {

    private static final SharedBuffer BLOCKING_QUEUE_BUFFER = new BlockingQueueBuffer();
    private static final SharedBuffer LOCK_CONDITION_BUFFER = new LockableSharedBuffer(5);

    public static void main(String[] args) {
        SharedBuffer boundedBuffer = LOCK_CONDITION_BUFFER;
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            //
            // ~ producer
            //
            executor.submit(() -> {
                try {
                    for (int i = 1; i <= 10; i++) {
                        //
                        // ~ blocks if full
                        //
                        boundedBuffer.write(i);
                        System.out.println("produced: " + i);
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            //
            // ~ consumer
            //
            executor.submit(() -> {
                try {
                    for (int i = 1; i <= 10; i++) {
                        //
                        // ~ blocks if empty
                        //
                        int value = boundedBuffer.read();
                        System.out.println("consumed: " + value);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    interface SharedBuffer {

        void write(int val);

        int read();

    }

    private static class BlockingQueueBuffer implements SharedBuffer {

        private final BlockingQueue<Integer> boundedBuffer = new ArrayBlockingQueue<>(5);

        @Override
        public void write(int val) {
            try {
                boundedBuffer.put(val);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public int read() {
            try {
                return boundedBuffer.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return -1;
            }
        }

    }

    private static class LockableSharedBuffer implements SharedBuffer {

        private final Lock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();
        private final int[] buffer;
        private final int capacity;
        private int putPtr = 0;
        private int takePtr = 0;
        private int count = 0;

        public LockableSharedBuffer(int capacity) {
            this.capacity = capacity;
            this.buffer = new int[capacity];
        }

        @Override
        public void write(int val) {
            lock.lock();
            try {
                while (count == capacity) {
                    try {
                        notFull.await();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;  // Exit on interrupt
                    }
                }
                buffer[putPtr] = val;
                putPtr = (putPtr + 1) % capacity;
                count++;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public int read() {
            lock.lock();
            try {
                while (count == 0) {
                    try {
                        notEmpty.await();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return -1;  // Return sentinel on interrupt
                    }
                }
                int val = buffer[takePtr];
                takePtr = (takePtr + 1) % capacity;
                count--;
                notFull.signal();
                return val;
            } finally {
                lock.unlock();
            }
        }
    }

}