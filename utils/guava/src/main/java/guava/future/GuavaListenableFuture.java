package guava.future;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;

class GuavaListenableFuture {

    public static void main(String[] args) throws InterruptedException {
        ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        ListenableFuture<Integer> asyncTask = executor.submit(() -> {
            System.out.println("running task");
            return Integer.MAX_VALUE;
        });
        asyncTask.addListener(() -> System.out.println("addListener()"), executor);
        Thread.sleep(500);
        executor.shutdown();
    }

}
