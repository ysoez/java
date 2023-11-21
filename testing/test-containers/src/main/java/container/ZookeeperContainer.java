package container;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.testcontainers.containers.ContainerLaunchException;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ZookeeperContainer extends GenericContainer<ZookeeperContainer> {

    private static final int PORT = 2181;
    private static final String IMAGE = "zookeeper:3.9.1";
    private final Timer timer = new Timer(true);

    public ZookeeperContainer() {
        super(IMAGE);
        withExposedPorts(PORT);
    }

    public ZooKeeper getConnection(int sessionTimeout, Watcher watcher) throws IOException {
        if (!isRunning()) {
            throw new ContainerLaunchException("Run container first");
        }
        return new ZooKeeper(connectString(), sessionTimeout, watcher);
    }

    public void executeAfter(Runnable runnable, long delay) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, delay);
    }

    public void stopAfter(long delay) {
        executeAfter(this::stop, delay);
    }

    private String connectString() {
        return getHost() + ":" + getMappedPort(PORT);
    }

}
