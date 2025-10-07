package multithreading.pc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ProducerConsumerBlockingQueue {

    public static void main(String[] args) {
        BlockingQueue<Integer> boundedBuffer = new ArrayBlockingQueue<>(5);
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
                        boundedBuffer.put(i);
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
                        int value = boundedBuffer.take();
                        System.out.println("consumed: " + value);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

}