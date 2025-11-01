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

    public static final String MASTER_ROOT = "/master";
    public static final String WORKER_ROOT = "/worker";

    private final ZooKeeper zoo;
    private final String namespace;
    private final Random random;

    private String currentNode;
    private List<String> allServiceAddresses;

    public ServiceRegistry(ZooKeeper zoo, String namespace) {
        this.zoo = zoo;
        this.namespace = namespace;
        this.random = new Random();
        init();
    }

    public void registerToCluster(String serverAddress) throws KeeperException, InterruptedException {
        if (this.currentNode != null) {
            log.info("already registered to service registry");
            return;
        }
        this.currentNode = zoo.create(namespace + "/n_", serverAddress.getBytes(), OPEN_ACL_UNSAFE, EPHEMERAL_SEQUENTIAL);
        log.info("registered to service registry: {}", currentNode);
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
            if (currentNode != null && zoo.exists(currentNode, false) != null) {
                zoo.delete(currentNode, -1);
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("failed to unregister from cluster", e);
        }
    }

    @Override
    public void process(WatchedEvent event) {
        try {
            updateAddresses();
        } catch (KeeperException | InterruptedException e) {
            log.error("failed to update addresses", e);
        }
    }

    private void init() {
        try {
            if (zoo.exists(namespace, false) == null) {
                zoo.create(namespace, new byte[]{}, OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            log.error("registry already exists", e);
        } catch (InterruptedException e) {
            log.error("cannot create registry", e);
        }
    }

    private synchronized void updateAddresses() throws KeeperException, InterruptedException {
        List<String> nodes = zoo.getChildren(namespace, this);
        List<String> addresses = new ArrayList<>(nodes.size());
        for (String node : nodes) {
            String nodeFullPath = namespace + "/" + node;
            Stat stat = zoo.exists(nodeFullPath, false);
            if (stat == null) {
                continue;
            }
            byte[] addressBytes = zoo.getData(nodeFullPath, false, stat);
            String address = new String(addressBytes);
            addresses.add(address);
        }
        this.allServiceAddresses = Collections.unmodifiableList(addresses);
        log.info("current cluster addresses: {}", this.allServiceAddresses);
    }

}
