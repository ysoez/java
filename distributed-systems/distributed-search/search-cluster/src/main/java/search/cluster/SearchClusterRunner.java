package search.cluster;

import cluster.ClusterConnector;
import cluster.election.LeaderElection;
import cluster.registry.ServiceRegistry;
import org.apache.zookeeper.ZooKeeper;
import search.cluster.election.OnElectionAction;

import static search.cluster.api.ServiceRegistryNamespace.MASTER_SERVICE_REGISTRY;
import static search.cluster.api.ServiceRegistryNamespace.WORKER_SERVICE_REGISTRY;

public class SearchClusterRunner {

    public static void main(String[] args) throws Exception {
        int currentServerPort = 8081;
        if (args.length == 1) {
            currentServerPort = Integer.parseInt(args[0]);
        }
        try (var clusterConnector = new ClusterConnector()) {
            ZooKeeper zooKeeper = clusterConnector.connect();
            var workersRegistry = new ServiceRegistry(zooKeeper, WORKER_SERVICE_REGISTRY);
            var coordinatorsRegistry = new ServiceRegistry(zooKeeper, MASTER_SERVICE_REGISTRY);
            var onElectionAction = new OnElectionAction(workersRegistry, coordinatorsRegistry, currentServerPort);
            var leaderElection = new LeaderElection(zooKeeper, onElectionAction);
            leaderElection.electLeader();
            clusterConnector.waitForDisconnect();
        }
        System.out.println("Disconnected from Zookeeper, exiting application");
    }

}
