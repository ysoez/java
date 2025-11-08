package cluster.election;

import cluster.ClusterConnector;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;

@Slf4j
public class LeaderElectionRunner {

    public static void main(String[] args) throws Exception {
        try (var connector = new ClusterConnector()) {
            ZooKeeper zooKeeper = connector.connect();
            LeaderElection leaderElection = new ZooKeeperLeaderElection(zooKeeper);
            //
            // ~ initial leader election
            //
            leaderElection.electLeader();
            connector.waitForDisconnect();
        }
        log.info("application exited");
    }

}