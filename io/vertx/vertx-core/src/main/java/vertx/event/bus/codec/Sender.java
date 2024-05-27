package vertx.event.bus.codec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;

import static java.lang.System.currentTimeMillis;

@Slf4j
public class Sender extends AbstractVerticle {

    public static void main(String[] args) {
        Launcher.executeCommand("run", Sender.class.getName(), "-cluster");
    }

    @Override
    public void start() throws Exception {
        EventBus eventBus = getVertx().eventBus();
        // ~ register codec for custom message
        eventBus.registerDefaultCodec(Message.class, new Message.Codec());

        // messages to send
        Message clusterWideMessage = new Message(currentTimeMillis(), 200, "Message sent from publisher!");
        Message localMessage = new Message(currentTimeMillis(), 200, "Local message!");

        // ~ send a message to [cluster receiver] every second
        getVertx().setPeriodic(1000, _id -> {
            eventBus.request("cluster-message-receiver", clusterWideMessage, reply -> {
                if (reply.succeeded()) {
                    var replyMessage = (Message) reply.result().body();
                    log.debug("Received reply: {}", replyMessage.content());
                    ;
                } else {
                    log.debug("No reply from cluster receiver");
                }
            });
        });

        // ~ deploy local receiver
        getVertx().deployVerticle(LocalReceiver.class.getName(), deployResult -> {
            // ~ deploy succeed
            if (deployResult.succeeded()) {
                // ~ send a message to [local receiver] every 2 second
                getVertx().setPeriodic(2000, _id -> {
                    eventBus.request("local-message-receiver", localMessage, reply -> {
                        if (reply.succeeded()) {
                            Message replyMessage = (Message) reply.result().body();
                            log.info("Received local reply: {}", replyMessage.content());
                        } else {
                            log.info("No reply from local receiver");
                        }
                    });
                });
            } else {
                log.error("Deploy failed", deployResult.cause());
            }
        });
    }

}
