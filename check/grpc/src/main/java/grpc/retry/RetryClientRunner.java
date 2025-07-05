package grpc.retry;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import static grpc.retry.RetryClient.ENV_DISABLE_RETRYING;

@Slf4j
class RetryClientRunner {

    public static void main(String[] args) throws Exception {
        boolean enableRetries = !Boolean.parseBoolean(System.getenv(ENV_DISABLE_RETRYING));
        var client = new RetryClient("localhost", 50051, enableRetries);
        try (var executor = new ForkJoinPool()) {
            for (int i = 0; i < 50; i++) {
                final String userId = "user" + i;
                executor.execute(() -> client.send(userId));
            }
            executor.awaitQuiescence(100, TimeUnit.SECONDS);
            executor.shutdown();
        } finally {
            client.printSummary();
            client.shutdown();
        }
    }

}