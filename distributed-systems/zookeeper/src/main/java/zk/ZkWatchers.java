package zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import zk.ZkUtils.ToxicRunnable;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static org.apache.zookeeper.CreateMode.EPHEMERAL;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

@Slf4j
@SuppressWarnings("SynchronizeOnNonFinalField")
class ZkWatchers implements Watcher, AutoCloseable {

    private static final String PARENT_NODE = "/parent";
    private final Timer timer = new Timer();
    private ZooKeeper zkClient;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        try (var app = new ZkWatchers()) {
            app.connectToServer();
            app.watchNode(PARENT_NODE);
            app.executeCommands();
            app.waitForDisconnect();
        }
    }

    @Override
    public void close() throws InterruptedException {
        zkClient.close();
        System.out.println("Gracefully closed the application");
    }

    private void connectToServer() throws IOException {
        zkClient = ZkUtils.newLocalClient(this);
    }

    private void executeCommands() {
        // ~ trigger NodeCreated
        scheduleCommand(() -> zkClient.create(PARENT_NODE, "p1".getBytes(), OPEN_ACL_UNSAFE, EPHEMERAL), 500);
        // ~ trigger NodeDataChanged
        scheduleCommand(() -> zkClient.setData(PARENT_NODE, "p2".getBytes(), 0), 1000);
        // ~ trigger NodeDeleted
        scheduleCommand(() -> zkClient.delete(PARENT_NODE, 1), 1500);
    }

    private void waitForDisconnect() throws InterruptedException {
        synchronized (zkClient) {
            zkClient.wait();
        }
    }

    private void watchNode(String path) throws InterruptedException, KeeperException {
        Stat stat = zkClient.exists(path, this);
        if (stat == null) {
            log.warn("Node not found: [{}]", path);
            return;
        }
        byte[] data = zkClient.getData(path, this, stat);
        List<String> children = zkClient.getChildren(path, this);
        log.info("Current state: path = [{}], data = [{}], children = [{}]", path, new String(data), children);
    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None -> {
                switch (event.getState()) {
                    case SyncConnected -> log.info("Successfully connected to Zookeeper server");
                    case Disconnected -> {
                        log.warn("Disconnected from Zookeeper server");
                        synchronized (zkClient) {
                            zkClient.notifyAll();
                        }
                    }
                }
            }
            case NodeCreated -> log.info("NodeCreated => [{}]", event);
            case NodeDeleted -> log.info("NodeDeleted => [{}]", event);
            case NodeDataChanged -> log.info("NodeDataChanged => [{}]", event);
            case NodeChildrenChanged -> log.info("NodeChildrenChanged => [{}]", event);
        }
        try {
            //
            // ~ receive and process up-to-date data & re-register one-time triggers
            //
            if (event.getPath() != null) {
                watchNode(event.getPath());
            }
        } catch (InterruptedException | KeeperException e) {
            log.error("Error happened", e);
        }
    }

    private void scheduleCommand(ToxicRunnable runnable, long delayMillis) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ZkUtils.run(runnable);
            }
        }, delayMillis);
    }

}
