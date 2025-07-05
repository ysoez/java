package vertx.quote;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import vertx.AbstractRestApiTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class QuotesRestApiTest extends AbstractRestApiTest {

    @Test
    void getQuoteForAsset(Vertx vertx, VertxTestContext context) {
        var client = newWebClient(vertx);
        client.get("/quotes/cache/AMZN").send().onComplete(context.succeeding(response -> {
            var json = response.bodyAsJsonObject();
            assertEquals("{\"name\":\"AMZN\"}", json.getJsonObject("asset").encode());
            assertEquals(200, response.statusCode());
            context.completeNow();
        }));
    }

    @Test
    void getQuoteForUnknownAssetReturnNotFound(Vertx vertx, VertxTestContext context) {
        var client = newWebClient(vertx);
        client.get("/quotes/cache/UNKNOWN").send().onComplete(context.succeeding(response -> {
            var json = response.bodyAsJsonObject();
            assertEquals(404, response.statusCode());
            assertEquals("{\"message\":\"quote for asset UNKNOWN not available!\",\"path\":\"/quotes/cache/UNKNOWN\"}", json.encode());
            context.completeNow();
        }));
    }

}
