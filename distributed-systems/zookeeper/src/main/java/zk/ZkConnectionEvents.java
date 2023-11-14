package zk;

import container.ZookeeperContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

import static zk.ZkUtils.SESSION_TIMEOUT;

@Slf4j
@SuppressWarnings("SynchronizeOnNonFinalField")
class ZkConnectionEvents implements Watcher {

    private ZooKeeper zkClient;

    public static void main(String[] args) throws IOException, InterruptedException {
        var app = new ZkConnectionEvents();
        try (ZookeeperContainer zookeeper = new ZookeeperContainer()) {
            zookeeper.start();
            app.waitForDisconnect(zookeeper);
        } finally {
            app.close();
        }
    }

    private void waitForDisconnect(ZookeeperContainer container) throws IOException, InterruptedException {
        zkClient = container.getConnection(SESSION_TIMEOUT, this);
        container.stopAfter(3000);
        synchronized (zkClient) {
            zkClient.wait();
        }
    }

    void close() throws InterruptedException {
        zkClient.close();
        System.out.println("Gracefully closed the application");
    }

    @Override
    public void process(WatchedEvent event) {
        // ~ connection & disconnection events comes in events with type None
        if (event.getType() != Event.EventType.None) {
            return;
        }
        if (event.getState() == Event.KeeperState.SyncConnected) {
            log.info("Successfully connected to Zookeeper server");
        } else {
            log.info("Disconnected from Zookeeper server: timeout={}, timeoutConfig={} ", zkClient.getSessionTimeout(), SESSION_TIMEOUT);
            synchronized (zkClient) {
                zkClient.notifyAll();
            }
        }
    }

}
