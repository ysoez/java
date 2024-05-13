package distributed;

import distributed.election.LeaderElection;
import distributed.registry.OnElectionAction;
import distributed.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;

@Slf4j
public class ServiceRegistryRunner {

    private static final int DEFAULT_PORT = 8080;
    private static final String REGISTRY_NAMESPACE = "/service_registry";

    public static void main(String[] args) throws Exception {
        int currentServerPort = args.length == 1 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

        try (var connector = new ClusterConnector()) {
            ZooKeeper zooKeeper = connector.connect();
            var serviceRegistry = new ServiceRegistry(zooKeeper, REGISTRY_NAMESPACE);
            var onElectionAction = new OnElectionAction(serviceRegistry, currentServerPort);
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
