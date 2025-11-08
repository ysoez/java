package cluster.registry;

import cluster.ClusterConnector;
import cluster.election.ElectionCallback;
import cluster.election.LeaderElection;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;

import java.net.InetAddress;

@Slf4j
public class ServiceRegistryRunner {

    private static final int DEFAULT_PORT = 8080;
    private static final String REGISTRY_NAMESPACE = "/service_registry";

    public static void main(String[] args) throws Exception {
        //
        // ~ use default port if running instances on different host
        // ~ use port from arguments if running multiple instances on the host
        //
        int serverPort = args.length == 1 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        try (var connector = new ClusterConnector()) {
            ZooKeeper zooKeeper = connector.connect();
            var serviceRegistry = new ZooKeepeerServiceRegistry(zooKeeper, REGISTRY_NAMESPACE);
            var leaderElection = new LeaderElection(zooKeeper, new ElectionCallback() {
                @Override
                public void onLeader() {
                    //
                    // ~ do nothing if a node just has joined a cluster
                    // ~ worker promoted to a leader unregister itself from the registry
                    //
                    serviceRegistry.unregister();
                    serviceRegistry.subscribeForUpdates();
                }
                @Override
                public void onWorker() {
                    try {
                        var host = InetAddress.getLocalHost().getCanonicalHostName();
                        var url = String.format("http://%s:%d", host, serverPort);
                        serviceRegistry.register(url);
                    } catch (Exception e) {
                        log.error("cannot register worker address in cluster", e);
                    }
                }
            });
            //
            // ~ initial leader election
            //
            leaderElection.electLeader();
            connector.waitForDisconnect();
        }
        log.debug("exiting application");
    }

}
