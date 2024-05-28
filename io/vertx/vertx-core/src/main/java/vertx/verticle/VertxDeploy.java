package vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxDeploy extends AbstractVerticle {

    public static void main(String[] args) {
        log.info("running application");
        var vertx = Vertx.vertx();
        vertx.deployVerticle(new VertxDeploy());
    }

    @Override
    public void start(Promise<Void> startPromise) {
        log.info("start: {}", getClass().getSimpleName());
        vertx.deployVerticle(new UI());
        vertx.deployVerticle(new IO(), whenDeployed -> {
            System.out.println("Deployed " + IO.class.getSimpleName());
            vertx.undeploy(whenDeployed.result());
        });
    }

    static class UI extends AbstractVerticle {
        @Override
        public void start(Promise<Void> startPromise) {
            log.info("start: {}", getClass().getSimpleName());
            vertx.deployVerticle(new RootComponent());
            startPromise.complete();
        }
    }

    static class RootComponent extends AbstractVerticle {
        @Override
        public void start(Promise<Void> startPromise) {
            log.info("start: {}", getClass().getSimpleName());
            startPromise.complete();
        }
    }

    static class IO extends AbstractVerticle {
        @Override
        public void start(Promise<Void> startPromise) {
            log.info("start: {}", getClass().getSimpleName());
            startPromise.complete();
        }
        @Override
        public void stop(Promise<Void> stopPromise) {
            log.info("stop: {}", getClass().getSimpleName());
            stopPromise.complete();
        }
    }

}