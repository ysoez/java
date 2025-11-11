package fe;

import cluster.ClusterConnector;
import cluster.http.server.SunWebServer;
import cluster.registry.ServiceRegistry;
import cluster.registry.ZooKeeperServiceRegistry;
import fe.handler.HomePageRequestHandler;
import fe.handler.SearchRequestHandler;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

import static cluster.registry.ZooKeeperServiceRegistry.MASTER_ROOT;

public class FrontendServerRunner {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 9000;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        try (var clusterConnector = new ClusterConnector()) {
            ZooKeeper zoo = clusterConnector.connect();
            ServiceRegistry coordinatorsRegistry = new ZooKeeperServiceRegistry(zoo, MASTER_ROOT);
            var webServer = new SunWebServer(port)
                    .addHandler(new SearchRequestHandler(coordinatorsRegistry))
                    .addHandler(new HomePageRequestHandler())
                    .withHealthCheck();
            webServer.start();

            System.out.println("server is listening on port: " + port);
            clusterConnector.waitForDisconnect();
        }
        System.out.println("application exited");
    }

}
