package election;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@SuppressWarnings("SynchronizeOnNonFinalField")
public class LeaderElection implements Watcher {

    private static final String LOCAL_ADDRESS = "localhost:2181";
    private static final int SESSION_TIMEOUT = 3000;
    private static final byte[] NO_DATA = new byte[]{};
    private static final String ELECTION_NAMESPACE = "/election";

    private ZooKeeper zooKeeper;
    private String currentZkNodeName;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        var app = new LeaderElection();
        try {
            app.connectToZookeeper();
            app.registerForLeadershipElection();
            app.reelectLeader();
            app.waitForDisconnect();
        } finally {
            app.close();
        }
        log.info("Disconnected from Zookeeper, exiting application");
    }

    public void registerForLeadershipElection() throws KeeperException, InterruptedException {
        String pathPrefix = ELECTION_NAMESPACE + "/c_";
        String fullPath = zooKeeper.create(pathPrefix, NO_DATA, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        log.info("I am: [{}]", fullPath);
        this.currentZkNodeName = fullPath.replace(ELECTION_NAMESPACE + "/", "");
    }

    public void reelectLeader() throws KeeperException, InterruptedException {
        Stat predecessorStat = null;
        String predecessorZnodeName = "";
        // ~ handle race condition
        while (predecessorStat == null) {
            List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);
            Collections.sort(children);
            String smallestChild = children.get(0);
            log.info("first [{}] vs me [{}]", smallestChild, currentZkNodeName);
            if (smallestChild.equals(currentZkNodeName)) {
                log.info("I am the leader");
                return;
            }
            // ~ worker (at least one node joined before)
            log.info("I am not the leader");
            int predecessorIndex = Collections.binarySearch(children, currentZkNodeName) - 1;
            predecessorZnodeName = children.get(predecessorIndex);
            predecessorStat = zooKeeper.exists(ELECTION_NAMESPACE + "/" + predecessorZnodeName, this);
            log.info("Watching node: [{}]", predecessorZnodeName);
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
                    reelectLeader();
                } catch (Exception e) {
                    log.error("Error happened during leader election", e);
                }
            }
        }
    }
}