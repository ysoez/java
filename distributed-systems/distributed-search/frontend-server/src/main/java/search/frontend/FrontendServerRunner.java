package search.frontend;

import cluster.ClusterConnector;
import cluster.network.http.WebServer;
import cluster.registry.ServiceRegistry;
import org.apache.zookeeper.ZooKeeper;
import search.frontend.handler.HomePageRequestHandler;
import search.frontend.handler.SearchRequestHandler;

import java.io.IOException;

import static search.cluster.api.ServiceRegistryNamespace.MASTER_SERVICE_REGISTRY;

public class FrontendServerRunner {

    public static void main(String[] args) throws IOException, InterruptedException {
        int currentServerPort = 9000;
        if (args.length == 1) {
            currentServerPort = Integer.parseInt(args[0]);
        }
        try (var clusterConnector = new ClusterConnector()){
            ZooKeeper zooKeeper = clusterConnector.connect();
            var coordinatorsServiceRegistry = new ServiceRegistry(zooKeeper, MASTER_SERVICE_REGISTRY);
            var webServer = new WebServer(currentServerPort)
                    .addHandler(new SearchRequestHandler(coordinatorsServiceRegistry))
                    .addHandler(new HomePageRequestHandler())
                    .withHealthCheck();
            webServer.startServer();

            System.out.println("Server is listening on port " + currentServerPort);
            clusterConnector.waitForDisconnect();
        }
        System.out.println("Disconnected from Zookeeper, exiting application");
    }

}
