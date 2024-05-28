package vertx.config;

import java.util.Set;

public class ConfigEnv {

    public static final String ENV_SERVER_PORT = "SERVER_PORT";

    public static final String ENV_DB_HOST = "DB_HOST";
    public static final String ENV_DB_PORT = "DB_PORT";
    public static final String ENV_DB_DATABASE = "DB_DATABASE";
    public static final String ENV_DB_USER = "DB_USER";
    public static final String ENV_DB_PASSWORD = "DB_PASSWORD";

    static final Set<String> EXPOSED_ENVIRONMENT_VARIABLES = Set.of(
        ENV_SERVER_PORT,
        ENV_DB_HOST, ENV_DB_PORT, ENV_DB_DATABASE, ENV_DB_USER, ENV_DB_PASSWORD
    );

}
