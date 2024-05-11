package grpc.error;

import static com.google.common.util.concurrent.MoreExecutors.directExecutor;
import static grpc.Utils.INSECURE_CHANNEL_CREDENTIALS;

import com.google.common.base.Verify;
import com.google.common.base.VerifyException;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.rpc.DebugInfo;
import grpc.echo.EchoReply;
import grpc.echo.EchoRequest;
import grpc.echo.EchoServiceGrpc;
import io.grpc.CallOptions;
import io.grpc.ClientCall;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.Server;
import io.grpc.Status;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

/**
 * Proto used here is just an example proto, but the pattern sending
 * application error information as an application-specific binary protos
 * in the response trailers is the recommended way to return application
 * level error.
 */
public class ErrorDetails {
    private static final Metadata.Key<DebugInfo> DEBUG_INFO_TRAILER_KEY = ProtoUtils.keyForProto(DebugInfo.getDefaultInstance());
    private static final DebugInfo DEBUG_INFO = DebugInfo.newBuilder()
            .addStackEntries("stack_entry_1")
            .addStackEntries("stack_entry_2")
            .addStackEntries("stack_entry_3")
            .setDetail("detailed error info.")
            .build();
    private static final String DEBUG_DESC = "detailed error description";

    public static void main(String[] args) throws Exception {
        Server server = Grpc.newServerBuilderForPort(0, InsecureServerCredentials.create())
                .addService(new EchoServiceGrpc.EchoServiceImplBase() {
                    @Override
                    public void echo(EchoRequest req, StreamObserver<EchoReply> resp) {
                        Metadata trailers = new Metadata();
                        trailers.put(DEBUG_INFO_TRAILER_KEY, DEBUG_INFO);
                        resp.onError(Status.INTERNAL.withDescription(DEBUG_DESC).asRuntimeException(trailers));
                    }})
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
        EchoServiceGrpc.EchoServiceBlockingStub stub = EchoServiceGrpc.newBlockingStub(channel);
        try {
            stub.echo(EchoRequest.newBuilder().build());
        } catch (Exception e) {
            verifyErrorReply(e);
        }
    }
    

    static void verifyErrorReply(Throwable t) {
        Status status = Status.fromThrowable(t);
        Metadata trailers = Status.trailersFromThrowable(t);
        Verify.verify(status.getCode() == Status.Code.INTERNAL);
        Verify.verify(trailers.containsKey(DEBUG_INFO_TRAILER_KEY));
        Verify.verify(status.getDescription().equals(DEBUG_DESC));
        try {
            Verify.verify(trailers.get(DEBUG_INFO_TRAILER_KEY).equals(DEBUG_INFO));
        } catch (IllegalArgumentException e) {
            throw new VerifyException(e);
        }
    }

    static void futureCallDirect(ManagedChannel channel) {
        EchoServiceGrpc.EchoServiceFutureStub stub = EchoServiceGrpc.newFutureStub(channel);
        ListenableFuture<EchoReply> response = stub.echo(EchoRequest.newBuilder().build());
        try {
            response.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            verifyErrorReply(e.getCause());
        }
    }

    static void futureCallCallback(ManagedChannel channel) {
        EchoServiceGrpc.EchoServiceFutureStub stub = EchoServiceGrpc.newFutureStub(channel);
        ListenableFuture<EchoReply> response = stub.echo(EchoRequest.newBuilder().build());
        final var latch = new CountDownLatch(1);
        Futures.addCallback(response, new FutureCallback<>() {
                    @Override
                    public void onSuccess(@Nullable EchoReply result) {
                        // Won't be called, since the server in this example always fails.
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        verifyErrorReply(t);
                        latch.countDown();
                    }
                },
                directExecutor());
        if (!Uninterruptibles.awaitUninterruptibly(latch, 1, TimeUnit.SECONDS)) {
            throw new RuntimeException("timeout!");
        }
    }

    static void asyncCall(ManagedChannel channel) {
        EchoServiceGrpc.EchoServiceStub stub = EchoServiceGrpc.newStub(channel);
        var request = EchoRequest.newBuilder().build();
        final CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<EchoReply> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(EchoReply value) {
                // Won't be called.
            }
            @Override
            public void onError(Throwable t) {
                verifyErrorReply(t);
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

    static void advancedAsyncCall(ManagedChannel channel) {
        ClientCall<EchoRequest, EchoReply> call = channel.newCall(EchoServiceGrpc.getEchoMethod(), CallOptions.DEFAULT);
        final var latch = new CountDownLatch(1);
        call.start(new ClientCall.Listener<>() {
            @Override
            public void onClose(Status status, Metadata trailers) {
                Verify.verify(status.getCode() == Status.Code.INTERNAL);
                Verify.verify(trailers.containsKey(DEBUG_INFO_TRAILER_KEY));
                try {
                    Verify.verify(trailers.get(DEBUG_INFO_TRAILER_KEY).equals(DEBUG_INFO));
                } catch (IllegalArgumentException e) {
                    throw new VerifyException(e);
                }
                latch.countDown();
            }
        }, new Metadata());
        call.sendMessage(EchoRequest.newBuilder().build());
        call.halfClose();
        if (!Uninterruptibles.awaitUninterruptibly(latch, 1, TimeUnit.SECONDS)) {
            throw new RuntimeException("timeout!");
        }
    }
}
