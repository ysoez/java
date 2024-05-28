package vertx.api;

import io.vertx.ext.web.Router;
import io.vertx.sqlclient.Pool;

public interface RestApi {

    String basePath();

    void bind(Router router, Pool pool);

}
