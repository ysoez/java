package vertx;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;

import static vertx.config.ConfigEnv.*;

//todo: add embedded postgres support
public abstract class AbstractRestApiTest {

    protected static final int TEST_SERVER_PORT = 9000;

    @BeforeEach
    void setup(Vertx vertx, VertxTestContext context) {
        System.setProperty(ENV_SERVER_PORT, String.valueOf(TEST_SERVER_PORT));
        System.setProperty(ENV_DB_HOST, "localhost");
        System.setProperty(ENV_DB_PORT, "5432");
        System.setProperty(ENV_DB_DATABASE, "vertx-stock-broker");
        System.setProperty(ENV_DB_USER, "postgres");
        System.setProperty(ENV_DB_PASSWORD, "secret");
        vertx.deployVerticle(new Application(), context.succeeding(id -> context.completeNow()));
    }

    protected WebClient newWebClient(final Vertx vertx) {
        return WebClient.create(vertx, new WebClientOptions().setDefaultPort(TEST_SERVER_PORT));
    }

}
