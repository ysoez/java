package vertx.websocket;

import io.vertx.core.Vertx;
import io.vertx.core.http.WebSocketConnectOptions;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(VertxExtension.class)
public class ApplicationTest {

    private static final int EXPECTED_MESSAGES = 5;

    @BeforeEach
    void deploy(Vertx vertx, VertxTestContext testContext) {
        vertx.deployVerticle(new Application(), testContext.succeeding(id -> testContext.completeNow()));
    }

    @Test
    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    void can_connect_to_web_socket_server(Vertx vertx, VertxTestContext context) {
        var client = vertx.createHttpClient();
        client.webSocket(8900, "localhost", WebSocketHandler.PATH)
            .onFailure(context::failNow)
            .onComplete(context.succeeding(ws -> ws.handler(data -> {
                    final var receivedData = data.toString();
                    log.debug("Received message: {}", receivedData);
                    assertEquals("Connected!", receivedData);
                    client.close();
                    context.completeNow();
                }))
            );
    }

    @Timeout(value = 10, timeUnit = TimeUnit.SECONDS)
    @Test
    void can_receive_multiple_messages(Vertx vertx, VertxTestContext context) {
        var client = vertx.createHttpClient();
        final AtomicInteger counter = new AtomicInteger(0);
        client.webSocket(new WebSocketConnectOptions()
                .setHost("localhost")
                .setPort(8900)
                .setURI(WebSocketHandler.PATH))
            .onFailure(context::failNow)
            .onComplete(context.succeeding(ws -> ws.handler(data -> {
                    final var receivedData = data.toString();
                    log.debug("Received message: {}", receivedData);
                    var currentValue = counter.getAndIncrement();
                    if (currentValue >= EXPECTED_MESSAGES) {
                        client.close();
                        context.completeNow();
                    } else {
                        log.debug("not enough messages yet... ({}/{})", currentValue, EXPECTED_MESSAGES);
                    }
                }))
            );
    }

}
