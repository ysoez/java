package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.sqlclient.Pool;
import lombok.extern.slf4j.Slf4j;
import vertx.api.AssetsRestApi;
import vertx.api.QuotesRestApi;
import vertx.api.RestApi;
import vertx.api.WatchListApi;
import vertx.config.Config;
import vertx.config.ConfigLoader;
import vertx.database.DatabasePools;

import java.util.Set;

@Slf4j
public class Api extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        ConfigLoader.loadAsync(vertx)
            .onFailure(startPromise::fail)
            .onSuccess(configuration -> {
                log.info("Retrieved Configuration: {}", configuration);
                createServer(configuration, startPromise);
            });
    }

    private void createServer(Config config, Promise<Void> startPromise) {
        Pool pool = createPool(config);
        Router router = Router.router(vertx);
        router.route()
            .handler(BodyHandler.create())
            .failureHandler(handleFailure());
        initApis().forEach(api -> api.bind(router, pool));

        vertx.createHttpServer()
            .requestHandler(router)
            .exceptionHandler(error -> log.error("HTTP Server error: ", error))
            .listen(config.getServerPort(), http -> {
                if (http.succeeded()) {
                    startPromise.complete();
                    log.info("HTTP server started on port {}", config.getServerPort());
                } else {
                    startPromise.fail(http.cause());
                }
            });
    }

    private Pool createPool(Config config) {
        return DatabasePools.createPgPool(config, vertx);
    }

    private Iterable<RestApi> initApis() {
        return Set.of(new AssetsRestApi(), new QuotesRestApi(), new WatchListApi());
    }

    private Handler<RoutingContext> handleFailure() {
        return errorContext -> {
            if (errorContext.response().ended()) {
                // connection closed
                return;
            }
            log.error("Route Error:", errorContext.failure());
            errorContext.response()
                .setStatusCode(500)
                .end(new JsonObject().put("message", "Something went wrong :(").toBuffer());
        };
    }

}
