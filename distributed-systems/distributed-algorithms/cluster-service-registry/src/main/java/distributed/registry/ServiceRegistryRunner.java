package distributed.registry;

import distributed.election.LeaderElection;
import distributed.ClusterConnector;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;

@Slf4j
public class ServiceRegistryRunner {

    private static final int DEFAULT_PORT = 8080;
    private static final String REGISTRY_NAMESPACE = "/service_registry";

    public static void main(String[] args) throws Exception {
        //
        // ~ use default port if instances are running on different machines
        // ~ use post from arguments if multiple instances are running on the same machine
        //
        int serverPort = args.length == 1 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        try (var connector = new ClusterConnector()) {
            ZooKeeper zooKeeper = connector.connect();
            var serviceRegistry = new ServiceRegistry(zooKeeper, REGISTRY_NAMESPACE);
            var onElectionAction = new OnElectionAction(serviceRegistry, serverPort);
            var leaderElection = new LeaderElection(zooKeeper, onElectionAction);
            //
            // ~ initial leader election
            //
            leaderElection.electLeader();
            connector.waitForDisconnect();
        }
        log.info("Disconnected from Zookeeper, exiting application");
    }

}
