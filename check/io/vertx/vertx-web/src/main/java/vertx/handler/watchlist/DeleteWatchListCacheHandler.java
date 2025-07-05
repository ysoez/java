package vertx.handler.watchlist;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vertx.handler.HandlerUtils;
import vertx.model.WatchList;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class DeleteWatchListCacheHandler implements Handler<RoutingContext> {

    private final Map<UUID, WatchList> watchListPerAccount;

    @Override
    public void handle(final RoutingContext context) {
        String accountId = HandlerUtils.getParam(context, "accountId");
        WatchList deleted = watchListPerAccount.remove(UUID.fromString(accountId));
        log.info("Deleted: {}, Remaining: {}", deleted, watchListPerAccount.values());
        context.response().end(deleted.toJson().toBuffer());
    }

}
