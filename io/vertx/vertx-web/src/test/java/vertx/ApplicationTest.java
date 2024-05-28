package vertx;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class ApplicationTest extends AbstractRestApiTest {

    @Test
    void applicationStarted(Vertx vertx, VertxTestContext testContext) {
        testContext.completeNow();
    }
    
}
