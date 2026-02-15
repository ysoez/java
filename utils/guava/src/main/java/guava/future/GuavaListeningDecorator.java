package guava.future;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class GuavaListeningDecorator {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
        try (ListeningScheduledExecutorService executor = MoreExecutors.listeningDecorator(scheduledExecutor)) {
            ListenableScheduledFuture<String> future = executor.schedule(() -> "hello", 1, TimeUnit.SECONDS);
            Futures.addCallback(future, new FutureCallback<>() {
                @Override
                public void onSuccess(String result) {
                    System.out.println(result);
                }
                @Override
                public void onFailure(Throwable t) {
                    System.err.println(t.getMessage());
                }
            }, MoreExecutors.directExecutor());
        }
    }

}
