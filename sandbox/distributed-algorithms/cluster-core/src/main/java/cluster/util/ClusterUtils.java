package cluster.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClusterUtils {

    public static final int DEFAULT_FRONTEND_SERVER_PORT = 9000;
    public static final int DEFAULT_SERVER_PORT = 8080;

    public static int parsePortOrDefault(String[] args, int defaultPort) {
        int port = defaultPort;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        return port;
    }

}
