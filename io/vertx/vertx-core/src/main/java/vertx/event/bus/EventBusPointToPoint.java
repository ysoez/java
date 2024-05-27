package vertx.event.bus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

public class EventBusPointToPoint {

    private static final String EVENT_BUS_ADDRESS = "sender-receiver-channel";

    //
    // ~ local mode
    //
    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        vertx.deployVerticle(new Sender());
        vertx.deployVerticle(new Receiver());
    }

    @Slf4j
    public static class Sender extends AbstractVerticle {

        //
        // ~ cluster mode
        //
        public static void main(String[] args) {
            Launcher.executeCommand("run", Sender.class.getName(), "-cluster");
        }

        @Override
        public void start() throws Exception {
            io.vertx.core.eventbus.EventBus eb = vertx.eventBus();
            // Send a message every second
            vertx.setPeriodic(1000, v -> {
                eb.request(EVENT_BUS_ADDRESS, "ping!", reply -> {
                    if (reply.succeeded()) {
                        log.info("Received reply: {}", reply.result().body());
                    } else {
                        log.info("No reply");
                    }
                });

            });
            log.info("Sender ready!");
        }
    }

    @Slf4j
    public static class Receiver extends AbstractVerticle {

        //
        // ~ cluster mode
        //
        public static void main(String[] args) {
            Launcher.executeCommand("run", Receiver.class.getName(), "-cluster");
        }

        @Override
        public void start() throws Exception {
            io.vertx.core.eventbus.EventBus eb = vertx.eventBus();
            eb.consumer(EVENT_BUS_ADDRESS, message -> {
                log.info("Received message: {}", message.body());
                // ~ send back reply
                message.reply("pong!");
            });
            log.info("Receiver ready!");
        }
    }

}
