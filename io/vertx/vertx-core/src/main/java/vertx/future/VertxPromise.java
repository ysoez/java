package vertx.future;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class VertxPromise {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        promiseSuccess(vertx);
        promiseFailure(vertx);
    }

    private static void promiseSuccess(Vertx vertx) {
        Promise<String> promise = Promise.promise();
        vertx.setTimer(500, id -> {
            promise.complete("done");
        });
    }

    private static void promiseFailure(Vertx vertx) {
        Promise<String> promise = Promise.promise();
        vertx.setTimer(500, id -> {
            promise.fail("done");
        });
    }

}
