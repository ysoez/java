package search.cluster.election;

import cluster.election.OnElectionCallback;
import cluster.network.http.WebServer;
import cluster.registry.ServiceRegistry;
import cluster.network.http.WebClient;
import search.cluster.handler.SearchCoordinatorRequestHandler;
import search.cluster.handler.SearchWorkerRequestHandler;
import org.apache.zookeeper.KeeperException;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class OnElectionAction implements OnElectionCallback {

    private final ServiceRegistry workersServiceRegistry;
    private final ServiceRegistry coordinatorsServiceRegistry;
    private final int port;
    private WebServer webServer;

    public OnElectionAction(ServiceRegistry workersServiceRegistry,
                            ServiceRegistry coordinatorsServiceRegistry,
                            int port) {
        this.workersServiceRegistry = workersServiceRegistry;
        this.coordinatorsServiceRegistry = coordinatorsServiceRegistry;
        this.port = port;
    }

    @Override
    public void onLeader() {
        workersServiceRegistry.unregisterFromCluster();
        workersServiceRegistry.registerForUpdates();

        if (webServer != null) {
            webServer.stop();
        }

        var searchCoordinator = new SearchCoordinatorRequestHandler(workersServiceRegistry, new WebClient());
        webServer = new WebServer(port).addHandler(searchCoordinator).withHealthCheck();
        webServer.startServer();

        try {
            String currentServerAddress = String.format(
                    "http://%s:%d%s", InetAddress.getLocalHost().getCanonicalHostName(),
                    port, searchCoordinator.endpoint());
            coordinatorsServiceRegistry.registerToCluster(currentServerAddress);
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

            workersServiceRegistry.registerToCluster(currentServerAddress);
        } catch (InterruptedException | UnknownHostException | KeeperException e) {
            e.printStackTrace();
        }
    }
}
