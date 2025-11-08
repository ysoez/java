package cluster.registry;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.*;

import static org.apache.zookeeper.CreateMode.EPHEMERAL_SEQUENTIAL;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

@Slf4j
public class ZooKeeperServiceRegistry implements ServiceRegistry, Watcher {

    public static final String MASTER_ROOT = "/master";
    public static final String WORKER_ROOT = "/worker";

    private final ZooKeeper zoo;
    private final String namespace;
    private final Random random;

    private String currentNode;
    private List<String> addressesCache;

    public ZooKeeperServiceRegistry(ZooKeeper zoo, String namespace) {
        this.zoo = zoo;
        this.namespace = namespace;
        this.random = new Random();
        createRegistry();
    }

    @Override
    public void register(String address) throws KeeperException, InterruptedException {
        if (this.currentNode != null) {
            log.info("already registered to service registry");
            return;
        }
        this.currentNode = zoo.create(namespace + "/n_", address.getBytes(), OPEN_ACL_UNSAFE, EPHEMERAL_SEQUENTIAL);
        log.info("{} is registered to {}", currentNode, namespace);
    }

    @Override
    public void unregister() {
        try {
            if (currentNode != null && zoo.exists(currentNode, false) != null) {
                zoo.delete(currentNode, -1);
                log.info("{} has unregistered from the cluster", currentNode);
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("failed to unregister from cluster", e);
        }
    }

    @Override
    public synchronized List<String> getServices() throws KeeperException, InterruptedException {
        if (addressesCache == null) {
            updateAddresses();
        }
        return addressesCache;
    }

    @Override
    public synchronized Optional<String> getRandomService() throws KeeperException, InterruptedException {
        if (addressesCache == null) {
            updateAddresses();
        }
        if (!addressesCache.isEmpty()) {
            int randomIndex = random.nextInt(addressesCache.size());
            return Optional.of(addressesCache.get(randomIndex));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void subscribeForUpdates() {
        try {
            updateAddresses();
        } catch (KeeperException | InterruptedException e) {
            log.error("failed to update addresses", e);
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

    private void createRegistry() {
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
        this.addressesCache = Collections.unmodifiableList(addresses);
        log.info("current cluster addresses: {}", this.addressesCache);
    }

}
