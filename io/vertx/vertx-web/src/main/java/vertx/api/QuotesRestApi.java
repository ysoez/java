package vertx.api;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.templates.SqlTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vertx.database.DatabaseUtils;
import vertx.model.Asset;
import vertx.model.Quote;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static vertx.api.AssetsRestApi.GetInMemoryAssetsHandler.ASSETS;
import static vertx.database.DatabaseUtils.onFail;

@Slf4j
public class QuotesRestApi implements RestApi {

    @Override
    public String basePath() {
        return "/quotes";
    }

    @Override
    public void bind(Router router, Pool pool) {
        router.get(basePath().concat("/cache/:asset")).handler(new GetCachedQuoteHandler(ASSETS));
        router.get(basePath().concat("/database/:asset")).handler(new GetDatabaseQuoteHandler(pool));
    }


    @Slf4j
    public static class GetCachedQuoteHandler implements Handler<RoutingContext> {

        private final Map<String, Quote> cache;

        public GetCachedQuoteHandler(Collection<String> assets) {
            this.cache = assets.stream().collect(Collectors.toMap(identity(), this::randomQuote));
        }

        @Override
        public void handle(final RoutingContext context) {
            final String asset = context.pathParam("asset");
            log.debug("getting quote for asset: {}", asset);
            var quote = cache.get(asset);
            if (quote == null) {
                DatabaseUtils.notFound(context, "quote for asset " + asset + " not available!");
                return;
            }
            var response = quote.toJson();
            log.debug("path={}, response: {}", context.normalizedPath(), response.encode());
            context.response().end(response.toBuffer());
        }

        private Quote randomQuote(String asset) {
            return Quote.builder()
                .asset(new Asset(asset))
                .bid(randomValue())
                .ask(randomValue())
                .lastPrice(randomValue())
                .volume(randomValue())
                .build();
        }

        private BigDecimal randomValue() {
            return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 100));
        }

    }

    @Slf4j
    @RequiredArgsConstructor
    public static class GetDatabaseQuoteHandler implements Handler<RoutingContext> {

        private final Pool db;

        @Override
        public void handle(final RoutingContext context) {
            final String asset = context.pathParam("asset");
            log.debug("getting quote for asset: {}", asset);
            SqlTemplate.forQuery(db, "SELECT q.asset, q.bid, q.ask, q.last_price, q.volume from broker.quotes q where asset=#{asset}")
                .mapTo(Quote.class)
                .execute(Collections.singletonMap("asset", asset))
                .onFailure(onFail(context, format("Failed to get quote for asset %s from db!", asset)))
                .onSuccess(quoteRowSet -> {
                    if (!quoteRowSet.iterator().hasNext()) {
                        DatabaseUtils.notFound(context, "quote for asset " + asset + " not available!");
                        return;
                    }
                    var response = quoteRowSet.iterator().next().toJson();
                    log.debug("path={}, response: {}", context.normalizedPath(), response.encode());
                    context.response()
                        .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                        .end(response.toBuffer());
                });
        }
    }

}
