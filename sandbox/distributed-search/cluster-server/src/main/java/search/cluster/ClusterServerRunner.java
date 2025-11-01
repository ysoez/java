package search.cluster;

import cluster.ClusterConnector;
import cluster.election.LeaderElection;
import cluster.registry.ServiceRegistry;
import org.apache.zookeeper.ZooKeeper;
import search.cluster.election.ClusterElectionCallback;

public class ClusterServerRunner {

    public static void main(String[] args) throws Exception {
        int currentServerPort = 8081;
        if (args.length == 1) {
            currentServerPort = Integer.parseInt(args[0]);
        }
        try (var clusterConnector = new ClusterConnector()) {
            ZooKeeper zooKeeper = clusterConnector.connect();
            var workersRegistry = new ServiceRegistry(zooKeeper, ServiceRegistry.WORKER_ROOT);
            var coordinatorsRegistry = new ServiceRegistry(zooKeeper, ServiceRegistry.MASTER_ROOT);
            var onElectionAction = new ClusterElectionCallback(workersRegistry, coordinatorsRegistry, currentServerPort);
            var leaderElection = new LeaderElection(zooKeeper, onElectionAction);
            leaderElection.electLeader();
            clusterConnector.waitForDisconnect();
        }
        System.out.println("Disconnected from Zookeeper, exiting application");
    }

}
