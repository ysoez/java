package guava.future;

import com.google.common.util.concurrent.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

class GuavaFutureAllAsList {

    public static void main(String[] args) throws InterruptedException {
        try (ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(3))){
            List<ListenableFuture<String>> futures = Arrays.asList(
                    executor.submit(() -> "task 1"),
                    executor.submit(() -> "task 2"),
                    executor.submit(() -> "task 3")
            );
            ListenableFuture<List<String>> allFutures = Futures.allAsList(futures);
            Futures.addCallback(allFutures, new FutureCallback<>() {
                @Override
                public void onSuccess(List<String> result) {
                    System.out.println("all tasks completed: " + result);
                }
                @Override
                public void onFailure(Throwable t) {
                    System.err.println("some task failed: " + t.getMessage());
                }
            }, MoreExecutors.directExecutor());
        }
    }

}
