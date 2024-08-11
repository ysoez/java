package grpc.retry;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import grpc.echo.EchoReply;
import grpc.echo.EchoRequest;
import grpc.echo.EchoServiceGrpc;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
class RetryClient {

    static final String ENV_DISABLE_RETRYING = "DISABLE_RETRYING_IN_RETRYING_EXAMPLE";

    private final boolean enableRetries;
    private final ManagedChannel channel;
    private final EchoServiceGrpc.EchoServiceBlockingStub blockingStub;
    private final AtomicInteger totalRpc = new AtomicInteger();
    private final AtomicInteger failedRpc = new AtomicInteger();

    RetryClient(String host, int port, boolean enableRetries) {
        ManagedChannelBuilder<?> channelBuilder = Grpc.newChannelBuilderForAddress(host, port, InsecureChannelCredentials.create());
        if (enableRetries) {
            Map<String, ?> serviceConfig = getRetryingServiceConfig();
            log.info("Client started with retrying configuration: {}", serviceConfig);
            channelBuilder.defaultServiceConfig(serviceConfig).enableRetry();
        }
        channel = channelBuilder.build();
        blockingStub = EchoServiceGrpc.newBlockingStub(channel);
        this.enableRetries = enableRetries;
    }

    void send(String message) {
        var request = EchoRequest.newBuilder().setMessage(message).build();
        EchoReply reply = null;
        StatusRuntimeException statusRuntimeException = null;
        try {
            reply = blockingStub.echo(request);
        } catch (StatusRuntimeException e) {
            failedRpc.incrementAndGet();
            statusRuntimeException = e;
        }
        totalRpc.incrementAndGet();
        if (statusRuntimeException == null) {
            log.info("Received response: {}", reply.getMessage());
        } else {
            log.warn("RPC failed: {}", statusRuntimeException.getStatus());
        }
    }

    void printSummary() {
        log.info("Total RPCs sent: {}, Total RPCs failed: {}", totalRpc.get(), failedRpc.get());
        if (enableRetries) {
            log.info("Retrying enabled. To disable retries, run the client with environment variable {}=true", ENV_DISABLE_RETRYING);
        } else {
            log.info("Retrying disabled. To enable retries, unset environment variable {} and then run the client", ENV_DISABLE_RETRYING);
        }
    }

    void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(60, TimeUnit.SECONDS);
    }

    private Map<String, ?> getRetryingServiceConfig() {
        InputStream retryPolicy = RetryClient.class.getClassLoader().getResourceAsStream("retrying_service_config.json");
        return new Gson().fromJson(new JsonReader(new InputStreamReader(retryPolicy, UTF_8)), Map.class);
    }

}