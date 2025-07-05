package vertx.future;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;
import io.vertx.core.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxFutureCompose extends AbstractVerticle {

    public static void main(String[] args) {
        Launcher.executeCommand("run", VertxFutureCompose.class.getName());
    }

    @Override
    public void start() throws Exception {
        composeSuccess();
        composeFailure();
    }

    private void composeSuccess() {
        Future<String> future = asyncActionOk();
        future.compose(this::asyncAction2).onComplete(ar -> {
            if (ar.failed()) {
                log.error("composition failed", ar.cause());
            } else {
                log.info("result: {}", ar.result());
            }
        });
    }

    private Future<String> asyncActionOk() {
        Promise<String> promise = Promise.promise();
        // ~ mimic something that take times
        vertx.setTimer(100, l -> promise.complete("world"));
        return promise.future();
    }

    private Future<String> asyncAction2(String name) {
        Promise<String> promise = Promise.promise();
        // ~ mimic something that take times
        vertx.setTimer(100, l -> promise.complete("hello " + name));
        return promise.future();
    }

    private void composeFailure() {
        asyncActionFail()
                .compose(result ->{ throw new RuntimeException("compose function mapper must not be called");})
                .onFailure(err -> log.error("error happened: {}", err.getMessage()));
    }

    private Future<String> asyncActionFail() {
        Promise<String> promise = Promise.promise();
        // ~ mimic something that take times
        vertx.setTimer(100, l -> promise.fail("connection lost"));
        return promise.future();
    }

}
