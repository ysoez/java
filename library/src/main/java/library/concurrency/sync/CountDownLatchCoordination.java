package library.concurrency.sync;

import java.util.concurrent.CountDownLatch;

import static library.concurrency.ConcurrencyUtils.*;

public class CountDownLatchCoordination {

    public static void main(String[] args) throws InterruptedException {
        int threadCount = Runtime.getRuntime().availableProcessors();
        var startLatch = new CountDownLatch(1);
        var endLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    //
                    // ~ wait to increase the likelihood of contention and simulate race condition
                    //
                    startLatch.await();
                    logWorker("Processing completed");
                    //
                    // ~ notify the main thread
                    //
                    endLatch.countDown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        logMain("Starting threads");
        //
        // ~ start the worker threads at once
        //
        startLatch.countDown();
        //
        // ~ wait until all the worker threads complete execution
        //
        endLatch.await();
        logMain("Program completed");
    }

}
