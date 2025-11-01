package cluster;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

@Slf4j
@SuppressWarnings("SynchronizeOnNonFinalField")
public class ClusterConnector implements Watcher, AutoCloseable {

    private ZooKeeper zoo;

    public ZooKeeper connect() throws IOException {
        return this.zoo = new ZooKeeper("localhost:2181", 3000, this);
    }

    public void waitForDisconnect() throws InterruptedException {
        synchronized (zoo) {
            zoo.wait();
            log.debug("disconnected from Zookeeper");
        }
    }

    @Override
    public void process(WatchedEvent event) {
        log.debug("{}", event);
        if (event.getType() != Event.EventType.None) {
            return;
        }
        switch (event.getState()) {
            case SyncConnected -> log.debug("connected to Zookeeper server");
            case Disconnected -> {
                synchronized (zoo) {
                    log.debug("disconnected from Zookeeper server");
                    zoo.notifyAll();
                }
            }
        }
    }

    @Override
    public void close() throws InterruptedException {
        zoo.close();
    }

}
