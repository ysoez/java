package vertx.future;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class VertxFuture {

    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        onSuccess(vertx);
        onFail(vertx);
    }

    private static void onSuccess(Vertx vertx) {
        Promise<String> promise = Promise.promise();
        vertx.setTimer(500, id -> {
            promise.complete("done");
        });
        Future<String> future = promise.future();
        future.onSuccess(result -> System.out.println("success"))
                .onFailure(err -> System.out.println("error"));
    }

    private static void onFail(Vertx vertx) {
        Promise<String> promise = Promise.promise();
        vertx.setTimer(500, id -> {
            promise.fail("done");
        });
        Future<String> future = promise.future();
        future.onSuccess(result -> System.out.println("success"))
                .onFailure(err -> System.out.println("error"));
    }

}
