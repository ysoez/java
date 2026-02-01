package guava.future;

import com.google.common.util.concurrent.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

class ListenableFutureSuccessfulOnly {

    public static void main(String[] args) throws InterruptedException {
        try (ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(3))) {
            List<ListenableFuture<String>> futures = Arrays.asList(
                    executor.submit(() -> { Thread.sleep(2000); return "slow task"; }),
                    executor.submit(() -> "fast task")
            );
            ListenableFuture<List<String>> anyFutures = Futures.successfulAsList(futures);
            Futures.addCallback(anyFutures, new FutureCallback<>() {
                @Override
                public void onSuccess(List<String> result) {
                    System.out.println("tasks completed (null if failed): " + result);
                }
                @Override
                public void onFailure(Throwable t) {
                    System.err.println("unexpected");
                }
            }, MoreExecutors.directExecutor());
        }
    }

}
