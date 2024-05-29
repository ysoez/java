package vertx.websocket;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.createHttpServer()
            .webSocketHandler(new WebSocketHandler(vertx))
            .listen(8900, http -> {
                if (http.succeeded()) {
                    startPromise.complete();
                    log.info("HTTP server started on port 8900");
                } else {
                    startPromise.fail(http.cause());
                }
            });
    }
}
