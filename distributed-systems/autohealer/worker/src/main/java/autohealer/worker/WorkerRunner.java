package autohealer.worker;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class WorkerRunner {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        var server = new WorkerServer();
        server.connectToZookeeper();
        server.run();
    }

}
