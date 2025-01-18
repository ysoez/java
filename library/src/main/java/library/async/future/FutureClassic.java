package library.async.future;

import java.util.ArrayList;
import java.util.concurrent.*;

import static library.concurrency.ConcurrencyUtils.*;

class FutureClassic {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        int tasks = cores;
        try (var threadPool = Executors.newFixedThreadPool(cores)) {
            var results = new ArrayList<Future<Long>>(tasks);
            for (int i = 0; i < tasks; i++) {
                Future<Long> result = threadPool.submit(new SlowOperation());
                results.add(result);
            }
            for (Future<Long> result : results) {
                Long value = result.get();
                logMain("Received value: " + value);
            }
        }
    }

    private static class SlowOperation implements Callable<Long> {
        @Override
        public Long call() {
            sleep(1, TimeUnit.SECONDS);
            long randomNumber = ThreadLocalRandom.current().nextLong(1_000_000, 2_000_000);
            logWorker("Calculated " + randomNumber);
            return randomNumber;
        }
    }

}
