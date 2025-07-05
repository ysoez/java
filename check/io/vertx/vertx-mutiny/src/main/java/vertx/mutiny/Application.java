package vertx.mutiny;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.Router;
import io.vertx.mutiny.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new Application()).subscribe().with(id -> log.info("Started: {}", id));
    }

    @Override
    public Uni<Void> asyncStart() {
        var router = Router.router(vertx);
        router.route().failureHandler(this::failureHandler);
        router.get("/users").respond(this::getUsers);
        return vertx.createHttpServer()
                .requestHandler(router)
                .listen(8111)
                .replaceWithVoid();
    }

    private Uni<JsonArray> getUsers(RoutingContext routingContext) {
        final var responseBody = new JsonArray();
        responseBody.add(new JsonObject().put("name", "Alice"));
        responseBody.add(new JsonObject().put("name", "Bob"));
        return Uni.createFrom().item(responseBody);
    }

    private void failureHandler(RoutingContext routingContext) {
        routingContext.response().setStatusCode(500).endAndForget("Something went wrong :(");
    }

}
