package vertx.event.bus.codec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalReceiver extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        EventBus eventBus = getVertx().eventBus();
        // ~ does not have to register codec because sender already registered

        // ~ receive message
        eventBus.consumer("local-message-receiver", message -> {
            Message inputMessage = (Message) message.body();
            log.info("Custom message received: {}", inputMessage.content());
            var replyMessage = new Message(System.currentTimeMillis(), 200, "Message sent from local receiver!");
            message.reply(replyMessage);
        });
    }

}
