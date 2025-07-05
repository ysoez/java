package vertx.handler;

import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HandlerUtils {

    public static String getParam(RoutingContext context, String paramName) {
        var paramValue = context.pathParam(paramName);
        log.info("path={}, param=[name={}, value={}]", context.normalizedPath(), paramName, paramValue);
        return paramValue;
    }

}
