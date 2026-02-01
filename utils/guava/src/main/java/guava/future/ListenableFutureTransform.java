package guava.future;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Executors;

class ListenableFutureTransform {

    public static void main(String[] args) throws InterruptedException {
        try (ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(2))) {
            ListenableFuture<Integer> future = service.submit(() -> 5);
            ListenableFuture<Integer> squaredFuture = Futures.transform(future, input -> input * input, service);
            Futures.addCallback(squaredFuture, new FutureCallback<>() {
                @Override
                public void onSuccess(Integer result) {
                    System.out.println("squared result: " + result);
                    service.shutdown();
                }
                @Override
                public void onFailure(Throwable t) {
                    System.err.println("failed: " + t.getMessage());
                    service.shutdown();
                }
            }, MoreExecutors.directExecutor());
        }
    }

}
