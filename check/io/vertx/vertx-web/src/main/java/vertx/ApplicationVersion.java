package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;
import vertx.config.ConfigLoader;

@Slf4j
public class ApplicationVersion extends AbstractVerticle {

    @Override
    public void start(final Promise<Void> startPromise) {
        ConfigLoader.loadAsync(vertx)
            .onFailure(startPromise::fail)
            .onSuccess(configuration -> {
                log.info("current application release version: {}", configuration.getVersion());
                startPromise.complete();
            });
    }

}
