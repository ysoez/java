package distributed.election;

import distributed.ClusterConnector;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;

@Slf4j
public class LeaderElectionRunner {

    public static void main(String[] args) throws Exception {
        try (var connector = new ClusterConnector()) {
            ZooKeeper zooKeeper = connector.connect();
            var leaderElection = new LeaderElection(zooKeeper);
            //
            // ~ initial leader election
            //
            leaderElection.electLeader();
            connector.waitForDisconnect();
        }
        log.info("Disconnected from Zookeeper, exiting application");
    }

}