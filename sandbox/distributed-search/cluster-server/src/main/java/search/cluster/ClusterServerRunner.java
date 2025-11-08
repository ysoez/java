package search.cluster;

import cluster.ClusterConnector;
import cluster.election.ElectionCallback;
import cluster.election.LeaderElection;
import cluster.election.ZooKeeperLeaderElection;
import cluster.registry.ServiceRegistry;
import cluster.registry.ZooKeeperServiceRegistry;
import org.apache.zookeeper.ZooKeeper;
import search.cluster.election.ClusterElectionCallback;

import static cluster.registry.ZooKeeperServiceRegistry.MASTER_ROOT;
import static cluster.registry.ZooKeeperServiceRegistry.WORKER_ROOT;

public class ClusterServerRunner {

    public static void main(String[] args) throws Exception {
        int serverPort = 8081;
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
        }
        try (var clusterConnector = new ClusterConnector()) {
            ZooKeeper zoo = clusterConnector.connect();
            ServiceRegistry workersRegistry = new ZooKeeperServiceRegistry(zoo, WORKER_ROOT);
            ServiceRegistry coordinatorsRegistry = new ZooKeeperServiceRegistry(zoo, MASTER_ROOT);
            ElectionCallback electionCallback = new ClusterElectionCallback(workersRegistry, coordinatorsRegistry, serverPort);
            LeaderElection leaderElection = new ZooKeeperLeaderElection(zoo, electionCallback);
            leaderElection.electLeader();
            clusterConnector.waitForDisconnect();
        }
        System.out.println("application exited");
    }

}
