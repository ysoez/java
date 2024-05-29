package vertx.websocket;

import io.vertx.core.Vertx;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
class PriceBroadcast {

    private final Map<String, ServerWebSocket> connectedClients = new HashMap<>();

    public PriceBroadcast(final Vertx vertx) {
        periodicUpdate(vertx);
    }

    private void periodicUpdate(final Vertx vertx) {
        vertx.setPeriodic(Duration.ofSeconds(1).toMillis(), id -> {
            log.debug("Push update to {} client(s)!", connectedClients.size());
            String priceUpdate = new JsonObject()
                .put("symbol", "AMZN")
                .put("value", new Random().nextInt(1000))
                .toString();
            connectedClients.values().forEach(ws ->
                ws.writeTextMessage(priceUpdate)
            );
        });
    }

    public void register(final ServerWebSocket ws) {
        connectedClients.put(ws.textHandlerID(), ws);
    }

    public void unregister(final ServerWebSocket ws) {
        connectedClients.remove(ws.textHandlerID());
    }

}
