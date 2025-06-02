package library.async.future;

import java.util.concurrent.*;

class FutureCancel {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var executor = Executors.newSingleThreadExecutor()) {
            var future = executor.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    return "Task Completed!";
                } catch (InterruptedException e) {
                    System.out.println("Task interrupted!");
                    return "Task Canceled!";
                }

            });

            TimeUnit.SECONDS.sleep(1);
            boolean cancelled = future.cancel(true);
            System.out.println("cancellation attempted: cancelled: " + cancelled);

            future.get();
        }
    }

}
