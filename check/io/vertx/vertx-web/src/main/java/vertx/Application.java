package vertx;

import io.vertx.core.*;
import lombok.extern.slf4j.Slf4j;
import vertx.config.ConfigLoader;
import vertx.database.FlywayMigration;

@Slf4j
public class Application extends AbstractVerticle {

    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        vertx.exceptionHandler(error -> log.error("unhandled error", error));
        vertx.deployVerticle(new Application())
            .onFailure(err -> log.error("application deployment failed", err))
            .onSuccess(id ->
                log.info("application successfully deployed: deploymentId={}", id)
            );
    }

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.deployVerticle(ApplicationVersion.class.getName())
            .onFailure(startPromise::fail)
            .compose(next -> migrateDatabase())
            .onFailure(startPromise::fail)
            .onSuccess(id -> log.info("migrated db schema to the latest version!"))
            .compose(next -> deployRestApi(startPromise));
    }

    private Future<Void> migrateDatabase() {
        return ConfigLoader.loadAsync(vertx).compose(config -> FlywayMigration.migrate(vertx, config.getDbConfig()));
    }

    private Future<String> deployRestApi(Promise<Void> startPromise) {
        var options = new DeploymentOptions()
            .setInstances(Math.max(1, Runtime.getRuntime().availableProcessors()));
        return vertx.deployVerticle(Api.class.getName(), options)
            .onFailure(startPromise::fail)
            .onSuccess(id -> {
                log.info("successfully deployed REST API");
                startPromise.complete();
            });
    }

}
