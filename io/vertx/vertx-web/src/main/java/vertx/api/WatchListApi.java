package vertx.api;

import io.vertx.ext.web.Router;
import io.vertx.sqlclient.Pool;
import lombok.extern.slf4j.Slf4j;
import vertx.handler.watchlist.*;
import vertx.model.WatchList;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class WatchListApi implements RestApi {

    @Override
    public String basePath() {
        return "/account/watchlist/:accountId";
    }

    @Override
    public void bind(Router router, Pool pool) {
        bindCachedEndpoints(router);
        bindDatabaseEndpoints(router, pool);
    }

    private void bindCachedEndpoints(Router router) {
        Map<UUID, WatchList> watchListPerAccount = new HashMap<>();
        router.get(basePath()).handler(new GetWatchListCacheHandler(watchListPerAccount));
        router.put(basePath()).handler(new PutWatchListCacheHandler(watchListPerAccount));
        router.delete(basePath()).handler(new DeleteWatchListCacheHandler(watchListPerAccount));
    }

    private void bindDatabaseEndpoints(Router router, Pool pool) {
        final String pgPath = "/pg/account/watchlist/database/:accountId";
        router.get(pgPath).handler(new GetWatchListDatabaseHandler(pool));
        router.put(pgPath).handler(new PutWatchListDatabaseHandler(pool));
        router.delete(pgPath).handler(new DeleteWatchListDatabaseHandler(pool));
    }


}
