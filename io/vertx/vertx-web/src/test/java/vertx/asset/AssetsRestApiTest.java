package vertx.asset;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import vertx.AbstractRestApiTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class AssetsRestApiTest extends AbstractRestApiTest {

    @Test
    void getAllFromCache(Vertx vertx, VertxTestContext context) {
        var client = newWebClient(vertx);
        client.get("/assets/cache").send().onComplete(context.succeeding(response -> {
            var json = response.bodyAsJsonArray();
            assertEquals("[\"AMZN\",\"APPL\",\"FB\",\"MSFT\",\"NFLX\"]", json.encode());
            assertEquals(200, response.statusCode());
            assertEquals(HttpHeaderValues.APPLICATION_JSON.toString(), response.getHeader(HttpHeaders.CONTENT_TYPE.toString()));
            context.completeNow();
        }));
    }

}
