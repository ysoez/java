package vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.ThreadingModel;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxScale extends AbstractVerticle {

    public static void main(String[] args) {
        Launcher.executeCommand("run", VertxScale.class.getName());
    }

    @Override
    public void start() throws Exception {
        // ~ deploy and don't wait for it to start
        vertx.deployVerticle(VertxScale.class.getName());

        // ~ deploy another instance and  want for it to start
        vertx.deployVerticle(VertxScale.class.getName(), res -> {
            if (res.succeeded()) {
                String deploymentID = res.result();
                log.info("deploy success: deploymentID={}", deploymentID);
                // ~ usually explicit undeploy not needed (recursive)
                vertx.undeploy(deploymentID, res2 -> {
                    if (res2.succeeded()) {
                        log.info("undeploy success");
                    } else {
                        log.error("undeploy failed", res2.cause());
                    }
                });
            } else {
                log.error("deployment failed", res.cause());
            }
        });

        // ~ deploy specifying config
        var options = new DeploymentOptions()
                .setConfig(new JsonObject().put("version", "1.0"))
                .setInstances(10)
                .setThreadingModel(ThreadingModel.WORKER);
        vertx.deployVerticle(VertxScale.class.getName(), options);
    }

}
