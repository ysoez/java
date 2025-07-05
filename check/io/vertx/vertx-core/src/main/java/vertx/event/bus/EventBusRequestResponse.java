package vertx.event.bus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

class EventBusRequestResponse {

    private static final String ADDRESS = "connection";

    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        vertx.deployVerticle(new Request());
        vertx.deployVerticle(new Response());
    }

    @Slf4j
    public static class Request extends AbstractVerticle {
        @Override
        public void start(Promise<Void> startPromise) {
            startPromise.complete();
            EventBus eventBus = vertx.eventBus();
            var message = new JsonObject().put("content", "Hello World");
            log.info("Sending message: {}", message);
            eventBus.request(ADDRESS, message, reply -> log.debug("Response: {}", reply.result().body()));
        }
    }

    @Slf4j
    public static class Response extends AbstractVerticle {
        @Override
        public void start(Promise<Void> startPromise) {
            startPromise.complete();
            vertx.eventBus().consumer(ADDRESS, message -> {
                log.info("Received message: {}", message.body());
                message.reply("Ack message: " + message.body(), new DeliveryOptions().setSendTimeout(1_000));
            });
        }
    }

}
