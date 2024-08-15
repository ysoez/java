package autohealer.watchdog;

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

    // ~ path to the worker jar
    private final String pathToProgram;

    // ~ the number of worker instances we need to maintain at all times
    private final int numberOfWorkers;
    private ZooKeeper zooKeeper;

    public Watchdog(int numberOfWorkers, String pathToProgram) {
        this.numberOfWorkers = numberOfWorkers;
        this.pathToProgram = pathToProgram;
    }

    void startWatchingWorkers() throws KeeperException, InterruptedException {
        if (zooKeeper.exists(WORKERS_NAMESPACE, false) == null) {
            zooKeeper.create(WORKERS_NAMESPACE, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        launchWorkersIfNecessary();
    }

    void connectToZookeeper() throws IOException {
        this.zooKeeper = new ZooKeeper(ZOOKEEPER_ADDRESS, SESSION_TIMEOUT, this);
    }

    void run() throws InterruptedException {
        synchronized (zooKeeper) {
            zooKeeper.wait();
        }
    }

    void close() throws InterruptedException {
        zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("Successfully connected to Zookeeper");
                } else {
                    synchronized (zooKeeper) {
                        System.out.println("Disconnected from Zookeeper event");
                        zooKeeper.notifyAll();
                    }
                }
                break;
            case NodeChildrenChanged:
                launchWorkersIfNecessary();
        }
    }

    private void launchWorkersIfNecessary() {
        try {
            List<String> children = zooKeeper.getChildren(WORKERS_NAMESPACE, this);
            log.info("Currently there are {} workers", children.size());
            if (children.size() < numberOfWorkers) {
                startNewWorker();
            }
        } catch (InterruptedException | KeeperException | IOException e) {
            log.error("Error happened, exiting app", e);
            System.exit(1);
        }
    }

    private void startNewWorker() throws IOException {
        var file = new File(pathToProgram);
        String command = "java -jar " + file.getName();
        log.info("Launching worker instance: {}", command);
        //noinspection deprecation
        Runtime.getRuntime().exec(command, null, file.getParentFile());
    }

}
