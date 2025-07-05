package vertx.watchlist;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import vertx.AbstractRestApiTest;
import vertx.model.Asset;
import vertx.model.WatchList;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class WatchListRestApiTest extends AbstractRestApiTest {

    @Test
    void putAssetToWatchlist(Vertx vertx, VertxTestContext context) {
        var client = newWebClient(vertx);
        var accountId = UUID.randomUUID();
        client.put("/account/watchlist/" + accountId).sendJsonObject(body())
            .onComplete(context.succeeding(response -> {
                var json = response.bodyAsJsonObject();
                assertEquals("{\"assets\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
                assertEquals(200, response.statusCode());
            }))
            .compose(next -> {
                client.get("/account/watchlist/" + accountId).send().onComplete(context.succeeding(response -> {
                    var json = response.bodyAsJsonObject();
                    assertEquals("{\"assets\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
                    assertEquals(200, response.statusCode());
                    context.completeNow();
                }));
                return Future.succeededFuture();
            });
    }

    @Test
    void deleteAssetFromWatchlist(Vertx vertx, VertxTestContext context) {
        var client = newWebClient(vertx);
        var accountId = UUID.randomUUID();
        client.put("/account/watchlist/" + accountId).sendJsonObject(body())
            .onComplete(context.succeeding(response -> {
                var json = response.bodyAsJsonObject();
                assertEquals("{\"assets\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
                assertEquals(200, response.statusCode());
            }))
            .compose(next -> {
                client.delete("/account/watchlist/" + accountId).send().onComplete(context.succeeding(response -> {
                    var json = response.bodyAsJsonObject();
                    assertEquals("{\"assets\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
                    assertEquals(200, response.statusCode());
                    context.completeNow();
                }));
                return Future.succeededFuture();
            });
    }

    private JsonObject body() {
        return new WatchList(Arrays.asList(new Asset("AMZN"), new Asset("TSLA"))).toJson();
    }

}
