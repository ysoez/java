package vertx.event.loop;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class WorkerThreadPool extends AbstractVerticle {

    public static void main(String[] args) {
        Launcher.executeCommand("run", WorkerThreadPool.class.getName());
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> {
            // ~ let's say we have to call a blocking API (e.g. JDBC) to execute a query for each request.
            // ~ we can't do this directly because it will block the event loop
            vertx.executeBlocking(() -> {
                // ~ imagine this was a call to a blocking API to get the result
                log.info("start running blocking code by worker pool");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    log.error("Interrupted ", e);
                }
                log.info("successfully executed blocking code");
                return Future.succeededFuture("done");
            }).onSuccess(result -> {
                log.info("blocking code result: {}", result);
                vertx.close();
            });

        }).listen(8080);

    }
}
