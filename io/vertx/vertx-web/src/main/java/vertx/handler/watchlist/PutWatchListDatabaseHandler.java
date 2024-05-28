package vertx.handler.watchlist;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.SqlResult;
import io.vertx.sqlclient.templates.SqlTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vertx.handler.HandlerUtils;
import vertx.model.WatchList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static vertx.database.DatabaseUtils.onFail;

@Slf4j
@RequiredArgsConstructor
public class PutWatchListDatabaseHandler implements Handler<RoutingContext> {

    private final Pool db;

    @Override
    public void handle(final RoutingContext context) {
        var accountId = HandlerUtils.getParam(context, "accountId");
        var json = context.body().asJsonObject();
        var watchList = json.mapTo(WatchList.class);

        var parameterBatch = watchList.getAssets().stream()
            .map(asset -> {
                final Map<String, Object> parameters = new HashMap<>();
                parameters.put("account_id", accountId);
                parameters.put("asset", asset.getName());
                return parameters;
            }).collect(Collectors.toList());
        db.withTransaction(client -> {
            // ~ 1: delete all for account_id
            return SqlTemplate.forUpdate(client,
                    "DELETE FROM broker.watchlist w where w.account_id = #{account_id}")
                .execute(Collections.singletonMap("account_id", accountId))
                .onFailure(onFail(context, "Failed to clear watchlist for accountId: " + accountId))
                .compose(deletionDone -> {
                    // ~ 2: add all for account_id
                    return addAllForAccountId(client, context, parameterBatch);
                })
                .onFailure(onFail(context, "Failed to update watchlist for accountId: " + accountId))
                .onSuccess(result ->
                    // ~ 3: both succeeded
                    context.response()
                        .setStatusCode(HttpResponseStatus.NO_CONTENT.code())
                        .end()
                );
        });
    }

    private Future<SqlResult<Void>> addAllForAccountId(final SqlConnection client,
                                                       final RoutingContext context,
                                                       final List<Map<String, Object>> parameterBatch) {
        return SqlTemplate.forUpdate(client, "INSERT INTO broker.watchlist VALUES (#{account_id},#{asset})"
                + " ON CONFLICT (account_id, asset) DO NOTHING")
            .executeBatch(parameterBatch)
            .onFailure(onFail(context, "Failed to insert into watchlist"));
    }
}
