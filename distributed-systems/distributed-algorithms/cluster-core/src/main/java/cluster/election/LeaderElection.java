package cluster.election;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;

@Slf4j
public class LeaderElection implements Watcher {

    private static final String ELECTION_NAMESPACE = "/election";
    private final ZooKeeper zooKeeper;
    private final OnElectionCallback onElectionCallback;
    private String currentNodeName;

    public LeaderElection(ZooKeeper zooKeeper) throws InterruptedException, KeeperException {
        this(zooKeeper, OnElectionCallback.NO_OP);
    }

    public LeaderElection(ZooKeeper zooKeeper, OnElectionCallback callback) throws InterruptedException, KeeperException {
        this.zooKeeper = zooKeeper;
        this.onElectionCallback = callback;
        registerForElection();
    }

    public void registerForElection() throws KeeperException, InterruptedException {
        String nodePrefix = ELECTION_NAMESPACE + "/c_";
        String nodeFullPath = zooKeeper.create(nodePrefix,
                new byte[]{},
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        log.info("Created node: {}", nodeFullPath);
        this.currentNodeName = nodeFullPath.replace(ELECTION_NAMESPACE + "/", "");
    }

    public void electLeader() throws KeeperException, InterruptedException {
        Stat predecessorStat = null;
        String predecessorNodeName;
        //
        // ~ handle race condition between getChildren() & exists()
        // ~ case 1: become a new leader
        // ~ case 2: find another predecessor node
        //
        while (predecessorStat == null) {
            List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);
            Collections.sort(children);
            String smallestChild = children.getFirst();
            log.info("Leader {}, current {}", smallestChild, currentNodeName);
            if (smallestChild.equals(currentNodeName)) {
                log.info("I am the leader");
                onElectionCallback.onLeader();
                return;
            }
            log.info("I am the worker");
            //
            // ~ if not a leader = at least one node joined before
            //
            int predecessorIndex = Collections.binarySearch(children, currentNodeName) - 1;
            predecessorNodeName = children.get(predecessorIndex);
            //
            // ~ subscribe for predecessor node notification
            //
            predecessorStat = zooKeeper.exists(ELECTION_NAMESPACE + "/" + predecessorNodeName, this);
            onElectionCallback.onWorker();
            log.info("Watching node: {}", predecessorNodeName);
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
        } catch (KeeperException | InterruptedException e) {
            log.error("Failed to elect a leader", e);
            throw new RuntimeException(e);
        }
    }

}

