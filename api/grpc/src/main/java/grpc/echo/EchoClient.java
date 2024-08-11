package grpc.echo;

import io.grpc.Channel;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class EchoClient {

    private final EchoServiceGrpc.EchoServiceBlockingStub blockingStub;

    EchoClient(Channel channel) {
        blockingStub = EchoServiceGrpc.newBlockingStub(channel);
    }

    void send(String message) {
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