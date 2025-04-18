package cluster.registry;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.apache.zookeeper.CreateMode.EPHEMERAL_SEQUENTIAL;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

@Slf4j
public class ServiceRegistry implements Watcher {

    private final ZooKeeper zooKeeper;
    private final String registryNamespace;
    private final Random random;
    private String currentNode;
    private List<String> allServiceAddresses;

    public ServiceRegistry(ZooKeeper zooKeeper, String registryNamespace) {
        this.zooKeeper = zooKeeper;
        this.registryNamespace = registryNamespace;
        this.random = new Random();
        createServiceRegistry();
    }

    public void registerToCluster(String serverAddress) throws KeeperException, InterruptedException {
        if (this.currentNode != null) {
            log.info("Already registered to service registry");
            return;
        }
        this.currentNode = zooKeeper.create(registryNamespace + "/n_", serverAddress.getBytes(), OPEN_ACL_UNSAFE, EPHEMERAL_SEQUENTIAL);
        log.info("Registered to service registry: {}", currentNode);
    }

    public void registerForUpdates() {
        try {
            updateAddresses();
        } catch (KeeperException | InterruptedException e) {
            log.error("Failed to update addresses", e);
        }
    }

    public synchronized List<String> getAllServiceAddresses() throws KeeperException, InterruptedException {
        if (allServiceAddresses == null) {
            updateAddresses();
        }
        return allServiceAddresses;
    }

    public synchronized String getRandomServiceAddress() throws KeeperException, InterruptedException {
        if (allServiceAddresses == null) {
            updateAddresses();
        }
        if (!allServiceAddresses.isEmpty()) {
            int randomIndex = random.nextInt(allServiceAddresses.size());
            return allServiceAddresses.get(randomIndex);
        } else {
            return null;
        }
    }

    public void unregisterFromCluster() {
        try {
            if (currentNode != null && zooKeeper.exists(currentNode, false) != null) {
                zooKeeper.delete(currentNode, -1);
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("Failed to unregister from cluster", e);
        }
    }

    @Override
    public void process(WatchedEvent event) {
        try {
            updateAddresses();
        } catch (KeeperException | InterruptedException e) {
            log.error("Failed to update addresses", e);
        }
    }

    private void createServiceRegistry() {
        try {
            if (zooKeeper.exists(registryNamespace, false) == null) {
                zooKeeper.create(registryNamespace, new byte[]{}, OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            log.error("Registry already exists", e);
        } catch (InterruptedException e) {
            log.error("Failed to create registry", e);
        }
    }

    private synchronized void updateAddresses() throws KeeperException, InterruptedException {
        List<String> workerNodes = zooKeeper.getChildren(registryNamespace, this);
        List<String> addresses = new ArrayList<>(workerNodes.size());
        for (String workerNode : workerNodes) {
            String workerFullPath = registryNamespace + "/" + workerNode;
            Stat stat = zooKeeper.exists(workerFullPath, false);
            if (stat == null) {
                continue;
            }
            byte[] addressBytes = zooKeeper.getData(workerFullPath, false, stat);
            String address = new String(addressBytes);
            addresses.add(address);
        }
        this.allServiceAddresses = Collections.unmodifiableList(addresses);
        log.info("The cluster addresses are: {}", this.allServiceAddresses);
    }

}
