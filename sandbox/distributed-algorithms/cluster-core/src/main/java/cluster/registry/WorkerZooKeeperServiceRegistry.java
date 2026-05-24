package cluster.registry;

import org.apache.zookeeper.ZooKeeper;

public class WorkerZooKeeperServiceRegistry extends ZooKeeperServiceRegistry {

    public WorkerZooKeeperServiceRegistry(ZooKeeper zoo) {
        super(zoo, "/worker");
    }

}
