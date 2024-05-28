package vertx.config;

import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.util.Objects;

@Builder
@Value
@ToString
public class Config {

    int serverPort;
    String version;
    DbConfig dbConfig;

    public static Config from(final JsonObject config) {
        final Integer serverPort = config.getInteger(ConfigEnv.ENV_SERVER_PORT);
        if (Objects.isNull(serverPort)) {
            throw new RuntimeException(ConfigEnv.ENV_SERVER_PORT + " not configured!");
        }

        final String version = config.getString("version");
        if (Objects.isNull(version)) {
            throw new RuntimeException("version is not configured in config file!");
        }

        return Config.builder()
            .serverPort(serverPort)
            .version(version)
            .dbConfig(parseDbConfig(config))
            .build();
    }

    private static DbConfig parseDbConfig(final JsonObject config) {
        return DbConfig.builder()
            .host(config.getString(ConfigEnv.ENV_DB_HOST))
            .port(config.getInteger(ConfigEnv.ENV_DB_PORT))
            .database(config.getString(ConfigEnv.ENV_DB_DATABASE))
            .user(config.getString(ConfigEnv.ENV_DB_USER))
            .password(config.getString(ConfigEnv.ENV_DB_PASSWORD))
            .build();
    }

}
