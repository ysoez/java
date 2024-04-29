package zk;

import container.ZookeeperContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

import static zk.ZkUtils.SESSION_TIMEOUT;
import static zk.ZkUtils.wrap;

@Slf4j
@SuppressWarnings("SynchronizeOnNonFinalField")
class ZkWatchers implements Watcher {
    private static final String PARENT_NODE = "/parent";
    private ZooKeeper zkClient;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        var app = new ZkWatchers();
        try (var container = new ZookeeperContainer()) {
            container.start();
            app.connectToServer(container);
            app.executeCommands(container);
            app.watchNode(PARENT_NODE);
            app.waitForDisconnect();
        } finally {
            app.close();
        }
    }

    private void connectToServer(ZookeeperContainer container) throws IOException {
        zkClient = container.getConnection(SESSION_TIMEOUT, this);
    }

    private void executeCommands(ZookeeperContainer container) {
        // ~ trigger NodeCreated
        container.executeAfter(wrap(() -> zkClient.create(PARENT_NODE, "p1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL)), 3000);
        // ~ trigger NodeDataChanged
        container.executeAfter(wrap(() -> zkClient.setData(PARENT_NODE, "p2".getBytes(), 0)), 3100);
        // ~ trigger NodeDeleted
        container.executeAfter(wrap(() -> zkClient.delete(PARENT_NODE, 1)), 3300);
        // ~ trigger Disconnected & stop container
        container.stopAfter(3500);
    }

    private void waitForDisconnect() throws InterruptedException {
        synchronized (zkClient) {
            zkClient.wait();
        }
    }

    private void close() throws InterruptedException {
        zkClient.close();
        System.out.println("Gracefully closed the application");
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
                        log.warn("Disconnected from Zookeeper server: timeout is " + zkClient.getSessionTimeout());
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

}
