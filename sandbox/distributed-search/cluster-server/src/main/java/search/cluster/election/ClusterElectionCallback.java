package search.cluster.election;

import cluster.election.ElectionCallback;
import cluster.http.client.JdkWebClient;
import cluster.http.server.sun.SunWebServer;
import cluster.http.server.WebServer;
import cluster.registry.ServiceRegistry;
import search.cluster.handler.SearchCoordinatorRequestHandler;
import search.cluster.handler.SearchWorkerRequestHandler;

import java.net.InetAddress;

public class ClusterElectionCallback implements ElectionCallback {

    private final ServiceRegistry workersServiceRegistry;
    private final ServiceRegistry coordinatorsServiceRegistry;
    private final int port;
    private WebServer webServer;

    public ClusterElectionCallback(ServiceRegistry workersServiceRegistry,
                                   ServiceRegistry coordinatorsServiceRegistry,
                                   int port) {
        this.workersServiceRegistry = workersServiceRegistry;
        this.coordinatorsServiceRegistry = coordinatorsServiceRegistry;
        this.port = port;
    }

    @Override
    public void onLeader() {
        workersServiceRegistry.unregister();
        workersServiceRegistry.subscribeForUpdates();

        if (webServer != null) {
            webServer.stop();
        }

        var searchCoordinator = new SearchCoordinatorRequestHandler(workersServiceRegistry, new JdkWebClient());
        webServer = new SunWebServer(port).addHandler(searchCoordinator).withHealthCheck();
        webServer.start();

        try {
            String currentServerAddress = String.format(
                    "http://%s:%d%s", InetAddress.getLocalHost().getCanonicalHostName(),
                    port, searchCoordinator.endpoint());
            coordinatorsServiceRegistry.register(currentServerAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWorker() {
        SearchWorkerRequestHandler searchWorker = new SearchWorkerRequestHandler();
        if (webServer == null) {
            webServer = new SunWebServer(port).addHandler(searchWorker).withHealthCheck();
            webServer.start();
        }

        try {
            String currentServerAddress =
                    String.format("http://%s:%d%s", InetAddress.getLocalHost().getCanonicalHostName(), port, searchWorker.endpoint());

            workersServiceRegistry.register(currentServerAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
