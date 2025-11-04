package watchdog;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class WatchdogRunner {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        if (args.length != 2) {
            System.out.println("expecting parameters <number of workers> <path to worker jar file>");
            System.exit(1);
        }
        int numberOfWorkers = Integer.parseInt(args[0]);
        String jarPath = args[1];
        var watchdog = new Watchdog(numberOfWorkers, jarPath);
        watchdog.connectToZookeeper();
        watchdog.watchWorkers();
        watchdog.waitForDisconnect();
        watchdog.close();
    }

}
