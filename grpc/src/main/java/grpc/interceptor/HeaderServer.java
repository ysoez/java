package grpc.interceptor;

import grpc.echo.EchoReply;
import grpc.echo.EchoRequest;
import grpc.echo.EchoServiceGrpc;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class HeaderServer {
    private static final Logger logger = Logger.getLogger(HeaderServer.class.getName());
    private static final int PORT = 50051;
    private Server server;

    public static void main(String[] args) throws IOException, InterruptedException {
        final var server = new HeaderServer();
        server.start();
        server.blockUntilShutdown();
    }

    private void start() throws IOException {
        server = Grpc.newServerBuilderForPort(PORT, InsecureServerCredentials.create())
                .addService(ServerInterceptors.intercept(new EchoService(), new HeaderServerInterceptor()))
                .build()
                .start();
        logger.info("Server started, listening on " + PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                HeaderServer.this.stop();
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

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    private static class EchoService extends EchoServiceGrpc.EchoServiceImplBase {
        @Override
        public void echo(EchoRequest req, StreamObserver<EchoReply> responseObserver) {
            var reply = EchoReply.newBuilder().setMessage("server: " + req.getMessage()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}