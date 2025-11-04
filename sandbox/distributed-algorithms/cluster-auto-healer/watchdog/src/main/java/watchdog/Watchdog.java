package watchdog;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@SuppressWarnings("SynchronizeOnNonFinalField")
class Watchdog implements Watcher {

    private static final String ZOOKEEPER_ADDRESS = "localhost:2181";
    private static final int SESSION_TIMEOUT = 3000;
    private static final String WORKERS_NAMESPACE = "/workers";

    private final String jarPath;
    private final int minNumOfWorkers;

    private ZooKeeper zoo;

    Watchdog(int minNumOfWorkers, String jarPath) {
        this.minNumOfWorkers = minNumOfWorkers;
        this.jarPath = jarPath;
    }

    void watchWorkers() throws KeeperException, InterruptedException {
        if (zoo.exists(WORKERS_NAMESPACE, false) == null) {
            zoo.create(WORKERS_NAMESPACE, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        launchWorkersIfNecessary();
    }

    void connectToZookeeper() throws IOException {
        this.zoo = new ZooKeeper(ZOOKEEPER_ADDRESS, SESSION_TIMEOUT, this);
    }

    void waitForDisconnect() throws InterruptedException {
        synchronized (zoo) {
            zoo.wait();
        }
    }

    void close() throws InterruptedException {
        zoo.close();
    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                if (event.getState() != Event.KeeperState.SyncConnected) {
                    synchronized (zoo) {
                        log.info("disconnected from the server");
                        zoo.notifyAll();
                    }
                }
                break;
            case NodeChildrenChanged:
                launchWorkersIfNecessary();
        }
    }

    private void launchWorkersIfNecessary() {
        try {
            List<String> children = zoo.getChildren(WORKERS_NAMESPACE, this);
            log.info("currently running workers: {}", children.size());
            if (children.size() < minNumOfWorkers) {
                startNewWorker();
            }
        } catch (InterruptedException | KeeperException | IOException e) {
            log.error("error happened, exiting app", e);
            System.exit(1);
        }
    }

    private void startNewWorker() throws IOException {
        var file = new File(jarPath);
        var command = "java -jar " + file.getName();
        log.info("launching worker instance: {}", command);
        //noinspection deprecation
        Runtime.getRuntime().exec(command, null, file.getParentFile());
    }

}
