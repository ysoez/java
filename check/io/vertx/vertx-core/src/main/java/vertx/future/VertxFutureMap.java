package vertx.future;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxFutureMap {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Promise<String> promise = Promise.promise();
        vertx.setTimer(500, id -> promise.complete("done"));
        Future<String> future = promise.future();
        future.map(JsonObject::new)
                .onSuccess(jsonRes -> log.info("success: {}", jsonRes))
                .onFailure(err -> log.error("error", err));
    }

}
