package vertx.quarkus;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import static vertx.quarkus.PeriodicUserFetcher.ADDRESS;

@Slf4j
@ApplicationScoped
public class EventBusConsumer extends AbstractVerticle {

    @Override
    public Uni<Void> asyncStart() {
        vertx.eventBus().<JsonArray>consumer(ADDRESS, message -> log.info("Consumed from event bus: {}", message.body()));
        return Uni.createFrom().voidItem();
    }

}
