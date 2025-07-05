package grpc.keepalive;

import grpc.echo.EchoReply;
import grpc.echo.EchoRequest;
import grpc.echo.EchoServiceGrpc;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class KeepAliveClient {

    private final EchoServiceGrpc.EchoServiceBlockingStub blockingStub;

    //
    // ~ channel here is a Channel, not a ManagedChannel, so it is not this code's responsibility to shut it down.
    // ~ passing Channels to code makes code easier to test and makes it easier to reuse Channels.
    //
    KeepAliveClient(Channel channel) {
        blockingStub = EchoServiceGrpc.newBlockingStub(channel);
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
        log.info("Received response: {}", reply.getMessage());
    }

}