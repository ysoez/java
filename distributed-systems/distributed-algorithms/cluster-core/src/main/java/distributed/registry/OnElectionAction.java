package distributed.registry;

import distributed.election.OnElectionCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class OnElectionAction implements OnElectionCallback {

    private final ServiceRegistry serviceRegistry;
    private final int port;

    public OnElectionAction(ServiceRegistry serviceRegistry, int port) {
        this.serviceRegistry = serviceRegistry;
        this.port = port;
    }

    @Override
    public void onLeader() {
        //
        // ~ do nothing if a node just has joined a cluster
        // ~ unregister from the registry if a node used to be worker but now promoted to be a leader
        //
        serviceRegistry.unregisterFromCluster();
        serviceRegistry.registerForUpdates();
    }

    @Override
    public void onWorker() {
        try {
            String serverAddress = String.format("http://%s:%d", InetAddress.getLocalHost().getCanonicalHostName(), port);
            serviceRegistry.registerToCluster(serverAddress);
        } catch (InterruptedException | UnknownHostException | KeeperException e) {
            log.error("Worker failed to register in cluster", e);
        }
    }

}
