package vertx.database;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class DatabaseUtils {

    public static Handler<Throwable> onFail(final RoutingContext context, String message) {
        return error -> {
            log.error("Failure: ", error);
            context.response()
                .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .end(new JsonObject()
                    .put("message", message)
                    .put("path", context.normalizedPath())
                    .toBuffer()
                );
        };
    }

    public static void notFound(RoutingContext context, String message) {
        context.response()
            .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
            .end(new JsonObject()
                .put("message", message)
                .put("path", context.normalizedPath())
                .toBuffer()
            );
    }

}
