package zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

import static zk.ZkUtils.SESSION_TIMEOUT;

@Slf4j
@SuppressWarnings("SynchronizeOnNonFinalField")
class ZkConnectionEvents implements Watcher, AutoCloseable {

    private ZooKeeper zkClient;

    public static void main(String[] args) throws IOException, InterruptedException {
        try (var app = new ZkConnectionEvents()) {
            app.waitForDisconnect();
        }
    }

    private void waitForDisconnect() throws IOException, InterruptedException {
        zkClient = ZkUtils.newLocalClient(this);
        synchronized (zkClient) {
            zkClient.wait();
        }
    }

    @Override
    public void close() throws InterruptedException {
        zkClient.close();
        log.info("Gracefully closed the application");
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
