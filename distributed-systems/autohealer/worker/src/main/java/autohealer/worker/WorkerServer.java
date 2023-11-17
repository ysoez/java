package autohealer.worker;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.LockSupport;

@Slf4j
class WorkerServer {

    private static final String ZOOKEEPER_ADDRESS = "localhost:2181";
    private static final int SESSION_TIMEOUT = 3000;
    private static final String WORKERS_NAMESPACE = "/workers";
    private static final float CHANCE_TO_FAIL = 0.1F;

    private final Random random = new Random();
    private ZooKeeper zooKeeper;

    void connectToZookeeper() throws IOException {
        this.zooKeeper = new ZooKeeper(ZOOKEEPER_ADDRESS, SESSION_TIMEOUT, event -> {});
    }

    @SuppressWarnings("InfiniteLoopStatement")
    void run() throws KeeperException, InterruptedException {
        registerNode();
        while (true) {
            log.info("Working...");
            LockSupport.parkNanos(1000);
            if (random.nextFloat() < CHANCE_TO_FAIL) {
                log.error("Critical error happened");
                throw new RuntimeException("Do not want to work anymore!");
            }
        }
    }

    private void registerNode() throws KeeperException, InterruptedException {
        zooKeeper.create(WORKERS_NAMESPACE + "/worker_",
                new byte[]{},
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL
        );
    }
}
