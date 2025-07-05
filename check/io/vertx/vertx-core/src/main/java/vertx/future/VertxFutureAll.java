package vertx.future;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxFutureAll {

    public static void main(String[] args) {
        Promise<String> p1 = Promise.promise();
        Future<String> f1 = p1.future();

        Promise<String> p2 = Promise.promise();
        Future<String> f2 = p2.future();

        Promise<String> p3 = Promise.promise();
        Future<String> f3 = p3.future();

        // ~ all must complete
        Future.all(f1, f2, f3).onSuccess(result -> log.info("result: {}", result));

        Vertx.vertx().setTimer(500, id -> {
            p1.complete("1");
            p2.complete("2");
            p3.complete("3");
        });
    }

}
