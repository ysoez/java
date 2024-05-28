package vertx.database;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import lombok.experimental.UtilityClass;
import vertx.config.Config;

@UtilityClass
public class DatabasePools {

    public static Pool createMySQLPool(final Config configuration, final Vertx vertx) {
        var connectOptions = new MySQLConnectOptions()
            .setHost(configuration.getDbConfig().getHost())
            .setPort(configuration.getDbConfig().getPort())
            .setDatabase(configuration.getDbConfig().getDatabase())
            .setUser(configuration.getDbConfig().getUser())
            .setPassword(configuration.getDbConfig().getPassword());
        var poolOptions = new PoolOptions()
            .setMaxSize(4);
        return MySQLPool.pool(vertx, connectOptions, poolOptions);
    }

    public static Pool createPgPool(final Config configuration, final Vertx vertx) {
        var connectOptions = new PgConnectOptions()
            .setHost(configuration.getDbConfig().getHost())
            .setPort(configuration.getDbConfig().getPort())
            .setDatabase(configuration.getDbConfig().getDatabase())
            .setUser(configuration.getDbConfig().getUser())
            .setPassword(configuration.getDbConfig().getPassword());
        var poolOptions = new PoolOptions()
            .setMaxSize(4);
        return PgPool.pool(vertx, connectOptions, poolOptions);
    }

}
