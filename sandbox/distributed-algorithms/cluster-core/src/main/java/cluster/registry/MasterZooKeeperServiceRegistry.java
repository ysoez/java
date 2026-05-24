package cluster.registry;

import org.apache.zookeeper.ZooKeeper;

public class MasterZooKeeperServiceRegistry extends ZooKeeperServiceRegistry {

    public MasterZooKeeperServiceRegistry(ZooKeeper zoo) {
        super(zoo, "/master");
    }

}
