package grpc.echo;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
class EchoServer {

    private Server server;

    public static void main(String[] args) throws IOException, InterruptedException {
        var server = new EchoServer();
        server.start();
        server.blockUntilShutdown();
    }

    private void start() throws IOException {
        int port = 50051;
        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new DefaultEchoService())
                .build()
                .start();
        log.info("Server started, listening on: {}", port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // ~ use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                EchoServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    // ~ await termination on the main thread since the grpc library uses daemon threads.
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    static class DefaultEchoService extends EchoServiceGrpc.EchoServiceImplBase {
        @Override
        public void echo(EchoRequest req, StreamObserver<EchoReply> responseObserver) {
            var reply = EchoReply.newBuilder().setMessage("Hello " + req.getMessage()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}