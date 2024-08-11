package grpc.retry;

import grpc.echo.EchoReply;
import grpc.echo.EchoRequest;
import grpc.echo.EchoServiceGrpc;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
class RetryServer {

    private static final float UNAVAILABLE_PERCENTAGE = 0.5F;
    private static final Random random = new Random();

    private Server server;

    void start() throws IOException {
        int port = 50051;
        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new DefaultEchoService())
                .build()
                .start();
        log.info("Server started, listening on {}", port);
        var df = new DecimalFormat("#%");
        log.info("Responding as UNAVAILABLE to {} requests", df.format(UNAVAILABLE_PERCENTAGE));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                RetryServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }

    void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    static class DefaultEchoService extends EchoServiceGrpc.EchoServiceImplBase {

        AtomicInteger retryCounter = new AtomicInteger(0);

        @Override
        public void echo(EchoRequest request, StreamObserver<EchoReply> responseObserver) {
            int count = retryCounter.incrementAndGet();
            if (random.nextFloat() < UNAVAILABLE_PERCENTAGE) {
                log.info("Returning stubbed UNAVAILABLE error. count: {}", count);
                responseObserver.onError(Status.UNAVAILABLE.withDescription("Temporarily unavailable...").asRuntimeException());
            } else {
                log.info("Returning successful Hello response, count: {}", count);
                var reply = EchoReply.newBuilder().setMessage(request.getMessage()).build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();
            }
        }
    }

}