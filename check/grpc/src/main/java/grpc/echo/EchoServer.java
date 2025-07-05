package grpc.echo;

import grpc.service.DefaultEchoService;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static grpc.Utils.registerShutdownHook;

@Slf4j
class EchoServer {

    private Server server;

    void start() throws IOException {
        int port = 50051;
        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new DefaultEchoService())
                .build()
                .start();
        log.info("Server started, listening on: {}", port);
        registerShutdownHook(EchoServer.this::stop);
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    // ~ await termination on the main thread since the grpc library uses daemon threads.
    void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}