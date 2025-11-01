package zk;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.admin.ZooKeeperAdmin;

import java.io.IOException;

public class ZkUtils {

    static final int SESSION_TIMEOUT = 3000;

    public static ZooKeeper newLocalClient(Watcher watcher) throws IOException {
        return new ZooKeeper("localhost:2181", SESSION_TIMEOUT, watcher);
    }

    public static ZooKeeperAdmin newLocalAdminClient(Watcher watcher) throws IOException {
        return new ZooKeeperAdmin("localhost:2181", SESSION_TIMEOUT, watcher);
    }

    public static void run(ToxicRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface ToxicRunnable {
        void run() throws Exception;
    }


}
