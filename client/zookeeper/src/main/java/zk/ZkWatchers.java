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
    private ZooKeeper zoo;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        try (var app = new ZkWatchers()) {
            app.connectToServer();
            app.watchNode(PARENT_NODE);
            app.triggerEvents();
            app.waitForDisconnect();
        }
        log.info("application exited");
    }

    @Override
    public void close() throws InterruptedException {
        zoo.close();
        System.out.println("gracefully closed the application");
    }

    private void connectToServer() throws IOException {
        zoo = ZkUtils.newLocalClient(this);
    }

    private void triggerEvents() {
        scheduleCommand(() -> zoo.create(PARENT_NODE, "v1".getBytes(), OPEN_ACL_UNSAFE, EPHEMERAL), 500);
        scheduleCommand(() -> zoo.setData(PARENT_NODE, "v2".getBytes(), 0), 1000);
        scheduleCommand(() -> zoo.delete(PARENT_NODE, 1), 1500);
    }

    private void waitForDisconnect() throws InterruptedException {
        synchronized (zoo) {
            zoo.wait();
        }
    }

    private void watchNode(String path) throws InterruptedException, KeeperException {
        Stat stat = zoo.exists(path, this);
        if (stat == null) {
            log.warn("node not found: {}", path);
            return;
        }
        byte[] data = zoo.getData(path, this, stat);
        List<String> children = zoo.getChildren(path, this);
        log.info("path={}, data={}, children={}", path, new String(data), children);
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.None) {
            switch (event.getState()) {
                case SyncConnected -> log.info("connected to server");
                case Disconnected -> {
                    log.warn("disconnected from server");
                    synchronized (zoo) {
                        zoo.notifyAll();
                    }
                }
            }
        } else {
            log.info("{}", event);
        }
        try {
            //
            // ~ process up-to-date data and register one-time triggers
            //
            if (event.getPath() != null) {
                watchNode(event.getPath());
            }
        } catch (InterruptedException | KeeperException e) {
            log.error("cannot watch node anymore", e);
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
