package vertx.event.loop;

import io.vertx.core.*;
import io.vertx.core.DeploymentOptions;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class EventLoopBlocking extends AbstractVerticle {

    public static void main(String[] args) {
        var vertx = Vertx.vertx(new VertxOptions()
                .setMaxEventLoopExecuteTime(500)
                .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
                .setBlockedThreadCheckInterval(1)
                .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
        );
        vertx.deployVerticle(EventLoopBlocking.class.getName(), new DeploymentOptions().setInstances(4));
        vertx.setTimer(TimeUnit.SECONDS.toMillis(3), id -> vertx.close());
    }

    @Override
    public void start(Promise<Void> startPromise) throws InterruptedException {
        log.info("Start {}", getClass().getName());
        startPromise.complete();
        // ~ block thread & generate thread checker warning
        TimeUnit.SECONDS.sleep(5);
    }

}