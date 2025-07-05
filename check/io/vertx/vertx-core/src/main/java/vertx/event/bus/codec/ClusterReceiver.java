package vertx.event.bus.codec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClusterReceiver extends AbstractVerticle {

    //
    // ~ cluster mode
    //
    public static void main(String[] args) {
        Launcher.executeCommand("run", ClusterReceiver.class.getName(), "-cluster");
    }

    @Override
    public void start() throws Exception {
        EventBus eventBus = getVertx().eventBus();
        eventBus.registerDefaultCodec(Message.class, new Message.Codec());
        eventBus.consumer("cluster-message-receiver", message -> {
            var inputMessage = (Message) message.body();
            log.info("Custom message received: {}", inputMessage.content());
            var replyMessage = new Message(System.currentTimeMillis(), 200, "Message sent from cluster receiver!");
            message.reply(replyMessage);
        });
    }

}
