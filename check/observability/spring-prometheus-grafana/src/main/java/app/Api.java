package app;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class Api {

    private final RequestHandler handler;

    @GetMapping("/hello")
    public CompletableFuture<String> hello() throws InterruptedException {
        return handler.processRequest();
    }

}
