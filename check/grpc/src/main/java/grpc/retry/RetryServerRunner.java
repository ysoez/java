package grpc.retry;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class RetryServerRunner {

    public static void main(String[] args) throws IOException, InterruptedException {
        var server = new RetryServer();
        server.start();
        server.blockUntilShutdown();
    }

}