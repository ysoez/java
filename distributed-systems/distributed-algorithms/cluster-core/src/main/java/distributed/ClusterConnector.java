package distributed;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import zk.ZkUtils;

import java.io.IOException;

@Slf4j
@SuppressWarnings("SynchronizeOnNonFinalField")
public class ClusterConnector implements Watcher, AutoCloseable {

    private ZooKeeper zooKeeper;

    public ZooKeeper connect() throws IOException {
        return this.zooKeeper = ZkUtils.newLocalClient(this);
    }

    public void waitForDisconnect() throws InterruptedException {
        synchronized (zooKeeper) {
            zooKeeper.wait();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        log.info("Received: {}", event);
        if (event.getType() != Event.EventType.None) {
            return;
        }
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

    @Override
    public void close() throws Exception {
        zooKeeper.close();
    }

}
