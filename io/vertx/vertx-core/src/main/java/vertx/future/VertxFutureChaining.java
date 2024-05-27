package vertx.future;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxFutureChaining {

    public static void main(String[] args) {
        Vertx.vertx()
                .createHttpServer()
                .requestHandler(req -> System.out.println())
                .listen(10_000)
                .compose(server -> {
                    log.info("1");
                    return Future.succeededFuture(server);
                })
                .compose(server -> {
                    log.info("2");
                    return Future.succeededFuture(server);
                })
                .onFailure(System.err::println)
                .onSuccess(server -> log.info("started server on port: {}", server.actualPort()));
    }

}
