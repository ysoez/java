package fe;

import cluster.ClusterConnector;
import cluster.http.client.JdkHttpClient;
import cluster.http.server.engine.SunHttpServer;
import cluster.registry.MasterZooKeeperServiceRegistry;
import cluster.util.ClusterUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import fe.handler.HomePageRequestHandler;
import fe.handler.SearchRequestHandler;
import lombok.extern.slf4j.Slf4j;

import static cluster.util.ClusterUtils.DEFAULT_FRONTEND_SERVER_PORT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

@Slf4j
public class FrontendServerRunner {

    public static void main(String[] args) throws Exception {
        int port = ClusterUtils.parsePortOrDefault(args, DEFAULT_FRONTEND_SERVER_PORT);
        var httpClient = new JdkHttpClient();
        var jsonMapper = new ObjectMapper()
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(SNAKE_CASE);
        try (var clusterConnector = new ClusterConnector()) {
            var zoo = clusterConnector.connect();
            var coordinatorsRegistry = new MasterZooKeeperServiceRegistry(zoo);
            var httpServer = new SunHttpServer(port)
                    .addHandler(new HomePageRequestHandler())
                    .addHandler(new SearchRequestHandler(coordinatorsRegistry, httpClient, jsonMapper))
                    .withHealthCheck();
            httpServer.start();
            log.debug("server is listening on port: {}", port);
            clusterConnector.waitForDisconnect();
        } finally {
            log.debug("application exited");
        }
    }

}
