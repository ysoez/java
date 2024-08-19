package grpc.keepalive;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class KeepAliveServerRunner {

    public static void main(String[] args) throws IOException, InterruptedException {
        final var server = new KeepAliveServer();
        server.start();
        server.blockUntilShutdown();
    }

}