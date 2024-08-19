package grpc.keepalive;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
class KeepAliveClientRunner {

    public static void main(String[] args) throws Exception {
        String target = "localhost:50051";
        //
        // ~ set keep alive configuration values based on your environment
        //
        var channelBuilder = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create());
        //
        // ~ keepAliveTime: send pings every 10 seconds if there is no activity
        // ~ in reality set to appropriate value e.g. 5 minutes
        //
        channelBuilder.keepAliveTime(10, TimeUnit.SECONDS);
        //
        // ~ keepAliveTimeout: wait 1 second for ping ack before considering the connection dead
        // ~ in reality set to appropriate value e.g. 10 seconds
        // ~ only set such a small value e.g. 1 second in certain low latency environments
        //
        channelBuilder.keepAliveTimeout(1, TimeUnit.SECONDS);
        //
        // ~ keepAliveWithoutCalls: send pings even without active streams
        // ~ normally disable it.
        //
        channelBuilder.keepAliveWithoutCalls(true);

        ManagedChannel channel = channelBuilder.build();
        try {
            var client = new KeepAliveClient(channel);
            client.send("Keep-alive Demo");
            Thread.sleep(30000);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

}