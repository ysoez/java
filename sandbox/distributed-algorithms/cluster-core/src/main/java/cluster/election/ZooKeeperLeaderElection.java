package cluster.election;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.apache.zookeeper.CreateMode.EPHEMERAL_SEQUENTIAL;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

@Slf4j
public class ZooKeeperLeaderElection implements LeaderElection, Watcher {

    private static final String ELECTION_NAMESPACE = "/election";
    private static final byte[] NO_DATA = new byte[0];
    private final ZooKeeper zoo;
    private final ElectionCallback callback;
    private String currentNodeName;

    public ZooKeeperLeaderElection(ZooKeeper zoo) throws InterruptedException, KeeperException {
        this(zoo, ElectionCallback.NO_OP);
    }

    public ZooKeeperLeaderElection(ZooKeeper zoo, ElectionCallback callback) throws InterruptedException, KeeperException {
        this.zoo = Objects.requireNonNull(zoo);
        this.callback = Objects.requireNonNull(callback);
        registerForElection();
    }

    @Override
    public void electLeader() throws KeeperException, InterruptedException {
        Stat predecessorStat = null;
        String predecessorNodeName;
        //
        // ~ repeat until watcher is registered
        //
        while (predecessorStat == null) {
            List<String> children = zoo.getChildren(ELECTION_NAMESPACE, false);
            Collections.sort(children);
            String smallestChild = children.getFirst();
            if (smallestChild.equals(currentNodeName)) {
                log.info("elected as leader: {}", currentNodeName);
                callback.onLeader();
                return;
            }
            log.info("elected as worker: {}, leader: {}", currentNodeName, smallestChild);
            //
            // ~ at least one node joined before
            //
            int predecessorIndex = Collections.binarySearch(children, currentNodeName) - 1;
            predecessorNodeName = children.get(predecessorIndex);
            //
            // ~ try to subscribe for predecessor node notification
            //
            predecessorStat = zoo.exists(ELECTION_NAMESPACE + "/" + predecessorNodeName, this);
            callback.onWorker();
            log.info("watching: {}", predecessorNodeName);
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() != Event.EventType.NodeDeleted) {
            return;
        }
        try {
            //
            // ~ cluster has changed
            //
            electLeader();
        } catch (KeeperException e) {
            log.error("cannot elect a leader due to server error", e);
        } catch (InterruptedException e) {
            log.error("cannot elect a leader since server transaction was interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private void registerForElection() throws KeeperException, InterruptedException {
        String nodePrefix = ELECTION_NAMESPACE + "/c_";
        String nodeFullPath = zoo.create(nodePrefix, NO_DATA, OPEN_ACL_UNSAFE, EPHEMERAL_SEQUENTIAL);
        this.currentNodeName = nodeFullPath.replace(ELECTION_NAMESPACE + "/", "");
        log.info("created: {}", currentNodeName);
    }

}

