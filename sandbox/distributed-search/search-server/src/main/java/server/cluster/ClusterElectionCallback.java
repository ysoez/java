package server.cluster;

import cluster.election.ElectionCallback;
import cluster.http.client.JdkHttpClient;
import cluster.http.server.HttpServer;
import cluster.http.server.engine.SunHttpServer;
import cluster.registry.MasterZooKeeperServiceRegistry;
import cluster.registry.ServiceRegistry;
import cluster.registry.WorkerZooKeeperServiceRegistry;
import cluster.serialization.JavaSerializer;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.Objects;

@Slf4j
public class ClusterElectionCallback implements ElectionCallback {

    private final ServiceRegistry workersRegistry;
    private final ServiceRegistry coordinatorsRegistry;
    private final JavaSerializer serializer;
    private final int port;
    private HttpServer httpServer;

    public ClusterElectionCallback(
            WorkerZooKeeperServiceRegistry workersRegistry,
            MasterZooKeeperServiceRegistry coordinatorsRegistry,
            JavaSerializer serializer,
            int port) {
        this.workersRegistry = Objects.requireNonNull(workersRegistry);
        this.coordinatorsRegistry = Objects.requireNonNull(coordinatorsRegistry);
        this.serializer = Objects.requireNonNull(serializer);
        this.port = port;
    }

    @Override
    public void onLeader() {
        workersRegistry.unregister();
        workersRegistry.subscribeForUpdates();
        if (httpServer != null) {
            httpServer.stop();
        }
        var coordinator = new SearchCoordinator(workersRegistry, new JdkHttpClient(), serializer);
        httpServer = new SunHttpServer(port)
                .addHandler(coordinator)
                .withHealthCheck();
        httpServer.start();
        registerIn(coordinatorsRegistry, coordinator.endpoint());
    }

    @Override
    public void onWorker() {
        var searchWorker = new SearchWorker(serializer);
        if (httpServer == null) {
            httpServer = new SunHttpServer(port)
                    .addHandler(searchWorker)
                    .withHealthCheck();
            httpServer.start();
        }
        registerIn(workersRegistry, searchWorker.endpoint());
    }

    private void registerIn(ServiceRegistry registry, String endpoint) {
        try {
            var host = InetAddress.getLocalHost().getCanonicalHostName();
            var url = String.format("http://%s:%d%s", host, port, endpoint);
            registry.register(url);
        } catch (Exception e) {
            log.error("failed to register in registry {}", registry.namespace());
        }
    }

}
