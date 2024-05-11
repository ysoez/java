package grpc.serialization;

import grpc.echo.EchoReply;
import grpc.echo.EchoRequest;
import grpc.echo.EchoServiceGrpc;
import io.grpc.*;
import io.grpc.stub.AbstractStub;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static io.grpc.stub.ClientCalls.blockingUnaryCall;

@Slf4j
final class JsonClient {

    private final ManagedChannel channel;
    private final EchoJsonStub blockingStub;

    public JsonClient(String host, int port) {
        channel = Grpc.newChannelBuilderForAddress(host, port, InsecureChannelCredentials.create()).build();
        blockingStub = new EchoJsonStub(channel);
    }

    public static void main(String[] args) throws Exception {
        // ~ access a service running on the local machine on port 50051
        var client = new JsonClient("localhost", 50051);
        try {
            String message = "default message";
            if (args.length > 0) {
                message = args[0];
            }
            client.sendMessage(message);
        } finally {
            client.shutdown();
        }
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void sendMessage(String message) {
        log.info("Sending message: {}", message);
        var request = EchoRequest.newBuilder().setMessage(message).build();
        EchoReply reply;
        try {
            reply = blockingStub.invokeEchoMethod(request);
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            return;
        }
        log.info("Reply from server: {}", reply.getMessage());
    }

    static final class EchoJsonStub extends AbstractStub<EchoJsonStub> {

        static final MethodDescriptor<EchoRequest, EchoReply> ECHO_METHOD = EchoServiceGrpc.getEchoMethod()
                .toBuilder(
                        JsonMarshaller.jsonMarshaller(EchoRequest.getDefaultInstance()),
                        JsonMarshaller.jsonMarshaller(EchoReply.getDefaultInstance()))
                .build();

        EchoJsonStub(Channel channel) {
            super(channel);
        }

        EchoJsonStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        @Override
        protected EchoJsonStub build(Channel channel, CallOptions callOptions) {
            return new EchoJsonStub(channel, callOptions);
        }

        EchoReply invokeEchoMethod(EchoRequest request) {
            return blockingUnaryCall(getChannel(), ECHO_METHOD, getCallOptions(), request);
        }
    }
}
