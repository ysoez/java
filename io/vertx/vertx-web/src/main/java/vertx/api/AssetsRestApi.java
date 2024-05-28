package vertx.api;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Pool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vertx.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class AssetsRestApi implements RestApi {

    @Override
    public String basePath() {
        return "/assets";
    }

    @Override
    public void bind(Router router, Pool pool) {
        router.get(basePath().concat("/cache")).handler(new GetInMemoryAssetsHandler());
        router.get(basePath().concat("/database")).handler(new GetDatabaseAssetsHandler(pool));
    }

    @Slf4j
    public static class GetInMemoryAssetsHandler implements Handler<RoutingContext> {

        static final Set<String> ASSETS = Set.of("APPL", "AMZN", "FB", "NFLX", "MSFT");

        @Override
        public void handle(RoutingContext context) {
            List<String> assets = new ArrayList<>(ASSETS);
            assets.sort(Comparator.naturalOrder());
            var response = new JsonArray(assets);
            context.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .end(response.toBuffer());
            log.debug("path={}, response: {}", context.normalizedPath(), response);
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    public static class GetDatabaseAssetsHandler implements Handler<RoutingContext> {

        private final Pool db;

        @Override
        public void handle(final RoutingContext context) {
            db.query("SELECT a.value FROM broker.assets a")
                .execute()
                .onFailure(DatabaseUtils.onFail(context, "Failed to get assets from db!"))
                .onSuccess(result -> {
                    var response = new JsonArray();
                    result.forEach(row -> {
                        response.add(row.getValue("value"));
                    });
                    context.response()
                        .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                        .end(response.toBuffer());
                    log.info("path={}, response: {}", context.normalizedPath(), response.encode());
                })
            ;
        }
    }

}
