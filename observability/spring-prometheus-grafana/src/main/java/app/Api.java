package app;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@RestController
public class Api {

    @GetMapping("/hello")
    public CompletableFuture<String> hello() throws InterruptedException {
        return processRequest();
    }

    @Async
    public CompletableFuture<String> processRequest() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(5).toSeconds());
        return CompletableFuture.completedFuture("Request executed!");
    }

}
