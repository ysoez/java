package grpc.error;

import com.google.common.base.Verify;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.Uninterruptibles;
import grpc.echo.EchoReply;
import grpc.echo.EchoRequest;
import grpc.echo.EchoServiceGrpc;
import grpc.echo.EchoServiceGrpc.EchoServiceBlockingStub;
import grpc.echo.EchoServiceGrpc.EchoServiceFutureStub;
import grpc.echo.EchoServiceGrpc.EchoServiceStub;
import io.grpc.*;
import io.grpc.stub.StreamObserver;

import javax.annotation.Nullable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.MoreExecutors.directExecutor;
import static grpc.Utils.INSECURE_CHANNEL_CREDENTIALS;
import static grpc.Utils.RANDOM_PORT;

public class ErrorHandlingClient {

    public static void main(String[] args) throws Exception {
        Server server = Grpc.newServerBuilderForPort(RANDOM_PORT, InsecureServerCredentials.create())
                .addService(new EchoServiceGrpc.EchoServiceImplBase() {
                    @Override
                    public void echo(EchoRequest request, StreamObserver<EchoReply> responseObserver) {
                        // ~ the server will always fail, and we'll see this failure on client-side
                        // ~ the exception is not sent to the client, only the status code (i.e., INTERNAL) and description
                        responseObserver.onError(Status.INTERNAL.withDescription("not working today").asRuntimeException());
                    }
                })
                .build()
                .start();
        ManagedChannel channel = Grpc.newChannelBuilderForAddress("localhost", server.getPort(), INSECURE_CHANNEL_CREDENTIALS).build();
        blockingCall(channel);
        futureCallDirect(channel);
        futureCallCallback(channel);
        asyncCall(channel);
        advancedAsyncCall(channel);

        channel.shutdown();
        server.shutdown();
        channel.awaitTermination(1, TimeUnit.SECONDS);
        server.awaitTermination();
    }

    static void blockingCall(ManagedChannel channel) {
        EchoServiceBlockingStub stub = EchoServiceGrpc.newBlockingStub(channel);
        try {
            //noinspection ResultOfMethodCallIgnored
            stub.echo(EchoRequest.newBuilder().setMessage("hi").build());
        } catch (Exception e) {
            Status status = Status.fromThrowable(e);
            Verify.verify(status.getCode() == Status.Code.INTERNAL);
            Verify.verify(status.getDescription().contains("working"));
            // Cause is not transmitted over the wire.
        }
    }

    static void futureCallDirect(ManagedChannel channel) {
        EchoServiceFutureStub stub = EchoServiceGrpc.newFutureStub(channel);
        ListenableFuture<EchoReply> response = stub.echo(EchoRequest.newBuilder().setMessage("hi").build());
        try {
            response.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            Status status = Status.fromThrowable(e.getCause());
            Verify.verify(status.getCode() == Status.Code.INTERNAL);
            Verify.verify(status.getDescription().contains("today"));
            // Cause is not transmitted over the wire.
        }
    }

    static void futureCallCallback(ManagedChannel channel) {
        EchoServiceFutureStub stub = EchoServiceGrpc.newFutureStub(channel);
        ListenableFuture<EchoReply> response = stub.echo(EchoRequest.newBuilder().setMessage("hi").build());
        final CountDownLatch latch = new CountDownLatch(1);
        Futures.addCallback(response, new FutureCallback<>() {
                    @Override
                    public void onSuccess(@Nullable EchoReply result) {
                        // Won't be called, since the server in this example always fails.
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Status status = Status.fromThrowable(t);
                        Verify.verify(status.getCode() == Status.Code.INTERNAL);
                        Verify.verify(status.getDescription().contains("today"));
                        // Cause is not transmitted over the wire..
                        latch.countDown();
                    }
                },
                directExecutor());
        if (!Uninterruptibles.awaitUninterruptibly(latch, 1, TimeUnit.SECONDS)) {
            throw new RuntimeException("timeout!");
        }
    }

    static void asyncCall(ManagedChannel channel) {
        EchoServiceStub stub = EchoServiceGrpc.newStub(channel);
        var request = EchoRequest.newBuilder().setMessage("hey").build();
        final var latch = new CountDownLatch(1);
        var responseObserver = new StreamObserver<EchoReply>() {
            @Override
            public void onNext(EchoReply value) {
                // Won't be called.
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                Verify.verify(status.getCode() == Status.Code.INTERNAL);
                Verify.verify(status.getDescription().contains("today"));
                // Cause is not transmitted over the wire..
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                // Won't be called, since the server in this example always fails.
            }
        };
        stub.echo(request, responseObserver);
        if (!Uninterruptibles.awaitUninterruptibly(latch, 1, TimeUnit.SECONDS)) {
            throw new RuntimeException("timeout!");
        }
    }

    /**
     * This is more advanced and does not make use of the stub.
     * You should not normally need to do this, but here is how you would.
     */
    static void advancedAsyncCall(ManagedChannel channel) {
        ClientCall<EchoRequest, EchoReply> call = channel.newCall(EchoServiceGrpc.getEchoMethod(), CallOptions.DEFAULT);
        final var latch = new CountDownLatch(1);
        call.start(new ClientCall.Listener<>() {
            @Override
            public void onClose(Status status, Metadata trailers) {
                Verify.verify(status.getCode() == Status.Code.INTERNAL);
                Verify.verify(status.getDescription().contains("today"));
                // Cause is not transmitted over the wire.
                latch.countDown();
            }
        }, new Metadata());
        call.sendMessage(EchoRequest.newBuilder().setMessage("Marge").build());
        call.halfClose();
        if (!Uninterruptibles.awaitUninterruptibly(latch, 1, TimeUnit.SECONDS)) {
            throw new RuntimeException("timeout!");
        }
    }
}
