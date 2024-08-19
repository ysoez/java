package grpc.keepalive;

import grpc.service.DefaultEchoService;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static grpc.Utils.registerShutdownHook;

@Slf4j
public class KeepAliveServer {

    private Server server;

    void start() throws IOException {
        int port = 50051;
        ServerBuilder<?> serverBuilder = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new DefaultEchoService());
        //
        // ~ keepAliveTime: ping the client if it is idle for 5 seconds to ensure the connection is still active
        // ~ set to an appropriate value in reality e.g. in minutes.
        //
        serverBuilder.keepAliveTime(5, TimeUnit.SECONDS);
        //
        // ~ keepAliveTimeout: wait 1 second for the ping ack before assuming the connection is dead
        // ~ set to an appropriate value in reality e.g. 10 minutes
        //
        serverBuilder.keepAliveTimeout(1, TimeUnit.SECONDS);
        //
        // ~ permitKeepAliveTime: If a client pings more than once every 5 seconds, terminate the connection
        //
        serverBuilder.permitKeepAliveTime(5, TimeUnit.SECONDS);
        //
        // ~ permitKeepAliveWithoutCalls: allow pings even when there are no active streams
        //
        serverBuilder.permitKeepAliveWithoutCalls(true);
        //
        // ~ maxConnectionIdle: if a client is idle for 15 seconds, send a GOAWAY
        //
        serverBuilder.maxConnectionIdle(15, TimeUnit.SECONDS);
        //
        // ~ maxConnectionAge: If any connection is alive for more than 30 seconds, send a GOAWAY.
        //
        serverBuilder.maxConnectionAge(30, TimeUnit.SECONDS);
        //
        // ~ maxConnectionAgeGrace: allow 5 seconds for pending RPCs to complete before forcibly closing connections
        //
        serverBuilder.maxConnectionAgeGrace(5, TimeUnit.SECONDS);

        server = serverBuilder.build().start();
        log.info("Server started, listening on {}", port);
        registerShutdownHook(KeepAliveServer.this::stop);
    }

    void stop() throws InterruptedException {
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