package spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
class SpringAsyncService {

    @Async
    public CompletableFuture<Void> doHeavyWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(200);
        log.debug("Completed heavy work");
        var future = new CompletableFuture<Void>();
        future.complete(null);
        return future;
    }

    @Async("customExecutor")
    public Future<Void> apiCall() throws InterruptedException {
        var resultReady = new AtomicBoolean();
        TimeUnit.MILLISECONDS.sleep(10);
        resultReady.set(true);
        log.debug("Received API response");
        return new Future<>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }
            @Override
            public boolean isCancelled() {
                return false;
            }
            @Override
            public boolean isDone() {
                return resultReady.get();
            }
            @Override
            public Void get() {
                return null;
            }
            @Override
            public Void get(long timeout, TimeUnit unit) {
                return null;
            }
        };
    }

}
