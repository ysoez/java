package server;

import cluster.http.server.SunWebServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebServerRunner {

    public static void main(String[] args) {
        int serverPort = 8080;
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
        }
        new SunWebServer(serverPort)
                .withHealthCheck()
                .addHandler(new NumbersMultiplierRequestHandler())
                .start();
        log.info("server is listening on port {}", serverPort);
    }

}
