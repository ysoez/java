package grpc.interceptor;

import grpc.echo.EchoReply;
import grpc.echo.EchoRequest;
import grpc.echo.EchoServiceGrpc;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class HeaderClient {

    private final ManagedChannel originChannel;
    private final EchoServiceGrpc.EchoServiceBlockingStub blockingStub;

    public static void main(String[] args) throws Exception {
        var client = new HeaderClient("localhost", 50051);
        try {
            String message = "hello";
            if (args.length > 0) {
                message = args[0];
            }
            client.send(message);
        } finally {
            client.shutdown();
        }
    }

    private HeaderClient(String host, int port) {
        originChannel = Grpc.newChannelBuilderForAddress(host, port, InsecureChannelCredentials.create()).build();
        ClientInterceptor interceptor = new HeaderClientInterceptor();
        Channel channel = ClientInterceptors.intercept(originChannel, interceptor);
        blockingStub = EchoServiceGrpc.newBlockingStub(channel);
    }

    private void shutdown() throws InterruptedException {
        originChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private void send(String message) {
        log.info("Sending request: {}", message);
        EchoRequest request = EchoRequest.newBuilder().setMessage(message).build();
        EchoReply reply;
        try {
            reply = blockingStub.echo(request);
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            return;
        }
        log.info("Reply: " + reply.getMessage());
    }

}