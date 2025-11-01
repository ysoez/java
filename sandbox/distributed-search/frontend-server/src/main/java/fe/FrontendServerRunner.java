package fe;

import cluster.ClusterConnector;
import cluster.http.server.WebServer;
import cluster.registry.ServiceRegistry;
import org.apache.zookeeper.ZooKeeper;
import fe.handler.HomePageRequestHandler;
import fe.handler.SearchRequestHandler;

import java.io.IOException;

public class FrontendServerRunner {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 9000;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        try (var clusterConnector = new ClusterConnector()) {
            ZooKeeper zoo = clusterConnector.connect();
            var coordinatorsRegistry = new ServiceRegistry(zoo, ServiceRegistry.MASTER_ROOT);
            var webServer = new WebServer(port)
                    .addHandler(new SearchRequestHandler(coordinatorsRegistry))
                    .addHandler(new HomePageRequestHandler())
                    .withHealthCheck();
            webServer.startServer();

            System.out.println("server is listening on port: " + port);
            clusterConnector.waitForDisconnect();
        }
        System.out.println("exiting application");
    }

}
