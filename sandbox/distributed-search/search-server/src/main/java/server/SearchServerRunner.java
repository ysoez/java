package server;

import cluster.ClusterConnector;
import cluster.election.ZooKeeperLeaderElection;
import cluster.registry.MasterZooKeeperServiceRegistry;
import cluster.registry.WorkerZooKeeperServiceRegistry;
import cluster.serialization.JavaSerializer;
import cluster.util.ClusterUtils;
import lombok.extern.slf4j.Slf4j;
import server.cluster.ClusterElectionCallback;

@Slf4j
public class SearchServerRunner {

    public static void main(String[] args) throws Exception {
        int port = ClusterUtils.parsePortOrDefault(args, ClusterUtils.DEFAULT_SERVER_PORT);
        var serializer = new JavaSerializer();
        try (var clusterConnector = new ClusterConnector()) {
            var zoo = clusterConnector.connect();
            var workersRegistry = new WorkerZooKeeperServiceRegistry(zoo);
            var coordinatorsRegistry = new MasterZooKeeperServiceRegistry(zoo);
            var electionCallback = new ClusterElectionCallback(workersRegistry, coordinatorsRegistry, serializer, port);
            var leaderElection = new ZooKeeperLeaderElection(zoo, electionCallback);
            leaderElection.electLeader();
            clusterConnector.waitForDisconnect();
        } finally {
            log.debug("application exited");
        }
    }

}
