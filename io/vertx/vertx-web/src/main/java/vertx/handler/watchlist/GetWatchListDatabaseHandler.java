package vertx.handler.watchlist;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.templates.SqlTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vertx.database.DatabaseUtils;
import vertx.handler.HandlerUtils;

import java.util.Collections;

import static vertx.database.DatabaseUtils.onFail;

@Slf4j
@RequiredArgsConstructor
public class GetWatchListDatabaseHandler implements Handler<RoutingContext> {

    private final Pool db;

    @Override
    public void handle(final RoutingContext context) {
        var accountId = HandlerUtils.getParam(context, "accountId");
        SqlTemplate.forQuery(db, "SELECT w.asset FROM broker.watchlist w where w.account_id=#{account_id}")
            .mapTo(Row::toJson)
            .execute(Collections.singletonMap("account_id", accountId))
            .onFailure(onFail(context, "Failed to fetch watchlist for accountId: " + accountId))
            .onSuccess(assets -> {
                if (!assets.iterator().hasNext()) {
                    DatabaseUtils.notFound(context, "watchlist for accountId " + accountId + " is not available!");
                    return;
                }
                var response = new JsonArray();
                assets.forEach(response::add);
                log.info("Path {} responds with {}", context.normalizedPath(), response.encode());
                context.response()
                    .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                    .end(response.toBuffer());
            });
    }
}
