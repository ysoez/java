package vertx.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

import static vertx.config.ConfigEnv.EXPOSED_ENVIRONMENT_VARIABLES;

@Slf4j
public class ConfigLoader {

    public static final String CONFIG_FILE = "application.yml";

    public static Future<Config> loadAsync(Vertx vertx) {
        final var exposedKeys = new JsonArray(new ArrayList<>(EXPOSED_ENVIRONMENT_VARIABLES));
        log.debug("Fetch configuration for {}", exposedKeys.encode());

        var envStore = new ConfigStoreOptions()
            .setType("env")
            .setConfig(new JsonObject().put("keys", exposedKeys));
        var propertyStore = new ConfigStoreOptions()
            .setType("sys")
            .setConfig(new JsonObject().put("cache", false));
        var yamlStore = new ConfigStoreOptions()
            .setType("file")
            .setFormat("yaml")
            .setConfig(new JsonObject().put("path", CONFIG_FILE));
        // ~ order defines overload rules
        var options = new ConfigRetrieverOptions()
            .addStore(yamlStore)
            .addStore(propertyStore)
            .addStore(envStore);

        return ConfigRetriever.create(vertx, options).getConfig().map(Config::from);
    }

}
