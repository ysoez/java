package library.async.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

class FutureExecutionException {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var executor = Executors.newSingleThreadExecutor()) {
            var future = executor.submit(() -> {
                throw new IllegalStateException();
            });
            future.get();
        }
    }

}
