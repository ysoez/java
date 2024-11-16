package app;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Service
public class RequestHandler {

    @Async
    public CompletableFuture<String> processRequest() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(5).toMillis());
        return CompletableFuture.completedFuture("Request executed!");
    }

}
