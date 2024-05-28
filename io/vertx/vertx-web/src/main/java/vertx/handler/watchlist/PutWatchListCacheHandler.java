package vertx.handler.watchlist;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import vertx.handler.HandlerUtils;
import vertx.model.WatchList;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class PutWatchListCacheHandler implements Handler<RoutingContext> {

    private final Map<UUID, WatchList> watchListPerAccount;

    @Override
    public void handle(final RoutingContext context) {
        String accountId = HandlerUtils.getParam(context, "accountId");
        var json = context.body().asJsonObject();
        var watchList = json.mapTo(WatchList.class);
        watchListPerAccount.put(UUID.fromString(accountId), watchList);
        context.response().end(json.toBuffer());
    }

}
