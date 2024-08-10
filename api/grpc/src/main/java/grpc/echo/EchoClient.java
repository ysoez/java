package grpc.echo;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
class EchoClient {

    private final EchoServiceGrpc.EchoServiceBlockingStub blockingStub;

    EchoClient(Channel channel) {
        blockingStub = EchoServiceGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws Exception {
        // ~ access a service running on the local machine on port 50051
        String target = "localhost:50051";

        // ~ create a communication channel to the server, known as a Channel
        // ~ Channels are thread-safe and reusable, it is common to create channels at the beginning of your application
        // ~ and reuse them until the application shuts down.
        // ~ use plaintext insecure credentials to avoid needing TLS certificates
        // ~ to use TLS, use TlsChannelCredentials instead
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();
        try {
            var client = new EchoClient(channel);
            client.send("ping");
        } finally {
            // ~ ManagedChannels use resources like threads and TCP connections
            // ~ to prevent leaking these resources the channel should be shut down when it will no longer be used
            // ~ if it may be used again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    public void send(String message) {
        log.info("Sending message: {}", message);
        var request = EchoRequest.newBuilder().setMessage(message).build();
        EchoReply reply;
        try {
            reply = blockingStub.echo(request);
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            return;
        }
        log.info("Received: {}", reply.getMessage());
    }

}