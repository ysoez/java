package library.async.future;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

import static library.concurrency.ConcurrencyUtils.*;

class FutureClassic {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        int tasks = cores;
        try {
            ExecutorService threadPool = Executors.newFixedThreadPool(cores);
            for (int i = 0; i < tasks; i++) {
                threadPool.submit(new SlowOperation());
            }
            threadPool.shutdown();
            var worker = new Thread(() -> {
                while (!threadPool.isTerminated()) {
                    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));
                }
                logMain("all task has been completed");
            });
            worker.setName("worker");
            worker.setDaemon(false);
            worker.start();
        } finally {
            logMain("completed execution");
        }
    }

    private static class SlowOperation implements Callable<Long> {
        @Override
        public Long call() {
            sleep(new Random().nextInt(3), TimeUnit.SECONDS);
            long randomNumber = ThreadLocalRandom.current().nextLong(1_000_000, 2_000_000);
            logWorker("calculated " + randomNumber);
            return randomNumber;
        }
    }

}
