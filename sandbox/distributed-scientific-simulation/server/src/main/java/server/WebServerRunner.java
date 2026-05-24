package server;

import cluster.http.server.engine.SunHttpServer;
import cluster.util.ClusterUtils;
import lombok.extern.slf4j.Slf4j;

import static cluster.util.ClusterUtils.DEFAULT_SERVER_PORT;

@Slf4j
public class WebServerRunner {

    public static void main(String[] args) throws Exception {
        int port = ClusterUtils.parsePortOrDefault(args, DEFAULT_SERVER_PORT);
        new SunHttpServer(port)
                .withHealthCheck()
                .addHandler(new NumbersMultiplierRequestHandler())
                .start();
        log.info("server is listening on port {}", port);
    }

}
