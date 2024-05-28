package vertx.handler.watchlist;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.templates.SqlTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vertx.handler.HandlerUtils;

import java.util.Collections;

import static vertx.database.DatabaseUtils.onFail;

@Slf4j
@RequiredArgsConstructor
public class DeleteWatchListDatabaseHandler implements Handler<RoutingContext> {

    private final Pool db;

    @Override
    public void handle(final RoutingContext context) {
        var accountId = HandlerUtils.getParam(context, "accountId");
        SqlTemplate.forUpdate(db, "DELETE FROM broker.watchlist where account_id=#{account_id}")
            .execute(Collections.singletonMap("account_id", accountId))
            .onFailure(onFail(context, "Failed to delete watchlist for accountId: " + accountId))
            .onSuccess(result -> {
                log.debug("Deleted {} rows for accountId {}", result.rowCount(), accountId);
                context.response()
                    .setStatusCode(HttpResponseStatus.NO_CONTENT.code())
                    .end();
            });
    }
}
