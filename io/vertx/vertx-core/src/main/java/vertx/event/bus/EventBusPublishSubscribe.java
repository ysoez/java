package vertx.event.bus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;

class EventBusPublishSubscribe {

    private static final String EVENT_BUS_ADDRESS = "sender-receivers-channel";

    //
    // ~ local mode
    //
    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        vertx.deployVerticle(new Sender());
        vertx.deployVerticle(Receiver.class.getName(), new DeploymentOptions().setInstances(2));
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
            EventBus eb = vertx.eventBus();
            // ~ send a message every second
            vertx.setPeriodic(1000, v -> eb.publish(EVENT_BUS_ADDRESS, "Some news!"));
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
            EventBus eb = vertx.eventBus();
            eb.consumer(EVENT_BUS_ADDRESS, message -> log.info("Received news on consumer 1: {}", message.body()));
            eb.consumer(EVENT_BUS_ADDRESS, message -> log.info("Received news on consumer 2: {}", message.body()));
            log.info("Receiver ready!");
        }
    }

}
