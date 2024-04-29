package distributed;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.apache.zookeeper.CreateMode.EPHEMERAL_SEQUENTIAL;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

@Slf4j
@SuppressWarnings("SynchronizeOnNonFinalField")
public class LeaderElection implements Watcher {

    private static final String LOCAL_ADDRESS = "localhost:2181";
    private static final int SESSION_TIMEOUT = 3000;
    private static final byte[] NO_DATA = new byte[]{};
    private static final String ELECTION_NAMESPACE = "/election";

    private ZooKeeper zooKeeper;
    private String currentNodeName;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        var app = new LeaderElection();
        try {
            app.connectToZookeeper();
            app.registerForLeadershipElection();
            //
            // ~ initial leader election
            //
            app.reelectLeader();
            app.waitForDisconnect();
        } finally {
            app.close();
        }
        log.info("Disconnected from Zookeeper, exiting application");
    }

    public void registerForLeadershipElection() throws KeeperException, InterruptedException {
        String pathPrefix = ELECTION_NAMESPACE + "/c_";
        String fullPath = zooKeeper.create(pathPrefix, NO_DATA, OPEN_ACL_UNSAFE, EPHEMERAL_SEQUENTIAL);
        log.info("I am: [{}]", fullPath);
        this.currentNodeName = fullPath.replace(ELECTION_NAMESPACE + "/", "");
    }

    public void reelectLeader() throws KeeperException, InterruptedException {
        Stat predecessorStat = null;
        String predecessorNodeName;
        //
        // ~ handle race condition between zooKeeper.getChildren() & zooKeeper.exists()
        // ~ case 1: become a new leader
        // ~ case 2: find another predecessor node
        //
        while (predecessorStat == null) {
            List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);
            Collections.sort(children);
            String smallestChild = children.getFirst();
            log.info("first [{}] vs me [{}]", smallestChild, currentNodeName);
            if (smallestChild.equals(currentNodeName)) {
                log.info("I am the leader");
                return;
            }
            log.info("I am not the leader");
            //
            // ~ if not a leader = at least one node joined before
            //
            int predecessorIndex = Collections.binarySearch(children, currentNodeName) - 1;
            predecessorNodeName = children.get(predecessorIndex);
            //
            // ~ subscribe for predecessor node notification
            //
            predecessorStat = zooKeeper.exists(ELECTION_NAMESPACE + "/" + predecessorNodeName, this);
            log.info("Watching node: [{}]", predecessorNodeName);
        }
    }

    public void connectToZookeeper() throws IOException {
        this.zooKeeper = new ZooKeeper(LOCAL_ADDRESS, SESSION_TIMEOUT, this);
    }

    public void waitForDisconnect() throws InterruptedException {
        synchronized (zooKeeper) {
            zooKeeper.wait();
        }
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent event) {
        log.info("Received: {}", event);
        switch (event.getType()) {
            case None -> {
                switch (event.getState()) {
                    case SyncConnected -> log.info("Successfully connected to Zookeeper");
                    case Disconnected -> {
                        synchronized (zooKeeper) {
                            log.info("Disconnected from Zookeeper");
                            zooKeeper.notifyAll();
                        }
                    }
                }
            }
            case NodeDeleted -> {
                try {
                    //
                    // ~ cluster has changed
                    //
                    reelectLeader();
                } catch (Exception e) {
                    log.error("Error happened during leader election", e);
                }
            }
        }
    }
}