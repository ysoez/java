package search.cluster.election;

import cluster.election.ElectionCallback;
import cluster.http.client.JdkWebClient;
import cluster.http.server.WebServer;
import cluster.registry.ZooKeepeerServiceRegistry;
import org.apache.zookeeper.KeeperException;
import search.cluster.handler.SearchCoordinatorRequestHandler;
import search.cluster.handler.SearchWorkerRequestHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClusterElectionCallback implements ElectionCallback {

    private final ZooKeepeerServiceRegistry workersServiceRegistry;
    private final ZooKeepeerServiceRegistry coordinatorsServiceRegistry;
    private final int port;
    private WebServer webServer;

    public ClusterElectionCallback(ZooKeepeerServiceRegistry workersServiceRegistry,
                                   ZooKeepeerServiceRegistry coordinatorsServiceRegistry,
                                   int port) {
        this.workersServiceRegistry = workersServiceRegistry;
        this.coordinatorsServiceRegistry = coordinatorsServiceRegistry;
        this.port = port;
    }

    @Override
    public void onLeader() {
        workersServiceRegistry.unregister();
        workersServiceRegistry.subscribe();

        if (webServer != null) {
            webServer.stop();
        }

        var searchCoordinator = new SearchCoordinatorRequestHandler(workersServiceRegistry, new JdkWebClient());
        webServer = new WebServer(port).addHandler(searchCoordinator).withHealthCheck();
        webServer.startServer();

        try {
            String currentServerAddress = String.format(
                    "http://%s:%d%s", InetAddress.getLocalHost().getCanonicalHostName(),
                    port, searchCoordinator.endpoint());
            coordinatorsServiceRegistry.register(currentServerAddress);
        } catch (InterruptedException | UnknownHostException | KeeperException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void onWorker() {
        SearchWorkerRequestHandler searchWorker = new SearchWorkerRequestHandler();
        if (webServer == null) {
            webServer = new WebServer(port).addHandler(searchWorker).withHealthCheck();
            webServer.startServer();
        }

        try {
            String currentServerAddress =
                    String.format("http://%s:%d%s", InetAddress.getLocalHost().getCanonicalHostName(), port, searchWorker.endpoint());

            workersServiceRegistry.register(currentServerAddress);
        } catch (InterruptedException | UnknownHostException | KeeperException e) {
            e.printStackTrace();
        }
    }
}
