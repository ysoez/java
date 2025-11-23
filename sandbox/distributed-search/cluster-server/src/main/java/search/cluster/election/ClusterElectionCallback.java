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
    private WebServer<?> webServer;

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
        var coordinator = new SearchCoordinatorRequestHandler(workersServiceRegistry, new JdkWebClient());
        webServer = new SunWebServer(port)
                .addHandler(coordinator)
                .withHealthCheck();
        webServer.start();
        registerIn(coordinatorsServiceRegistry, coordinator.endpoint());
    }

    @Override
    public void onWorker() {
        var searchWorker = new SearchWorkerRequestHandler();
        if (webServer == null) {
            webServer = new SunWebServer(port)
                    .addHandler(searchWorker)
                    .withHealthCheck();
            webServer.start();
        }
        registerIn(workersServiceRegistry, searchWorker.endpoint());
    }

    private void registerIn(ServiceRegistry registry, String endpoint) {
        try {
            var host = InetAddress.getLocalHost().getCanonicalHostName();
            var currentServerAddress = String.format("http://%s:%d%s", host, port, endpoint);
            registry.register(currentServerAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
