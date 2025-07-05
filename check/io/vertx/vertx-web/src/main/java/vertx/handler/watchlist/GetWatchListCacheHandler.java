package vertx.handler.watchlist;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import vertx.model.WatchList;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static vertx.handler.HandlerUtils.getParam;

@RequiredArgsConstructor
public class GetWatchListCacheHandler implements Handler<RoutingContext> {

    private final Map<UUID, WatchList> watchListPerAccount;

    @Override
    public void handle(final RoutingContext context) {
        var accountId = getParam(context, "accountId");
        var watchList = Optional.ofNullable(watchListPerAccount.get(UUID.fromString(accountId)));
        if (watchList.isEmpty()) {
            context.response()
                .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
                .end(new JsonObject()
                    .put("message", "watchlist for account " + accountId + " not available!")
                    .put("path", context.normalizedPath())
                    .toBuffer()
                );
            return;
        }
        context.response().end(watchList.get().toJson().toBuffer());
    }

}
