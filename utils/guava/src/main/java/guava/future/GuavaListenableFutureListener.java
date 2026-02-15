package guava.future;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;

class GuavaListenableFutureListener {

    public static void main(String[] args) {
        try (ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor())) {
            ListenableFuture<Integer> asyncTask = executor.submit(() -> {
                System.out.println("running task");
                return Integer.MAX_VALUE;
            });
            asyncTask.addListener(() -> System.out.println("addListener()"), MoreExecutors.directExecutor());
        }
    }

}
