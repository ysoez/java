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

    private ZooKeeper zoo;

    public static void main(String[] args) throws IOException, InterruptedException {
        try (var app = new ZkConnectionEvents()) {
            app.waitForDisconnect();
        }
        log.info("application exited");
    }

    private void waitForDisconnect() throws IOException, InterruptedException {
        zoo = ZkUtils.newLocalClient(this);
        synchronized (zoo) {
            zoo.wait();
        }
    }

    @Override
    public void close() throws InterruptedException {
        zoo.close();
        log.info("application gracefully closed");
    }

    @Override
    public void process(WatchedEvent event) {
        //
        // ~ skip non-connection events
        //
        if (event.getType() != Event.EventType.None) {
            return;
        }
        if (event.getState() == Event.KeeperState.SyncConnected) {
            log.info("connected to Zookeeper server");
        } else {
            log.info("disconnected from Zookeeper server: timeout={}, maxTimeout={}", zoo.getSessionTimeout(), SESSION_TIMEOUT);
            synchronized (zoo) {
                zoo.notifyAll();
            }
        }
    }

}
