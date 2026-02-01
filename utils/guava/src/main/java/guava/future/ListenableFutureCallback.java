package guava.future;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Executors;

class ListenableFutureCallback {

    public static void main(String[] args) throws InterruptedException {
        try (ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(2))) {
            ListenableFuture<String> future = executor.submit(() -> {
                Thread.sleep(1000);
                return "running task";
            });
            Futures.addCallback(future, new FutureCallback<>() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("task succeeded with result: " + result);
                }

                @Override
                public void onFailure(Throwable t) {
                    System.err.println("task failed: " + t.getMessage());
                }
            }, MoreExecutors.directExecutor());
        }

    }

}
