package server;

import cluster.http.server.handler.AbstractSunHttpRequestHandler;
import cluster.http.server.handler.HealthCheckRequestHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

@Slf4j
public class WebServerRunner {

    private final int port;

    public static void main(String[] args) throws IOException {
        int serverPort = 8080;
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
        }
        var webServer = new WebServerRunner(serverPort);
        webServer.start();
        log.info("Server is listening on port {}", serverPort);
    }

    public WebServerRunner(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            log.error("Failed to start a server", e);
            throw e;
        }
        registerEndpoint(server, new HealthCheckRequestHandler());
        registerEndpoint(server, new NumbersMultiplierHttpRequestHandler());
        setThreadPool(server);
        server.start();
    }

    private void registerEndpoint(HttpServer server, AbstractSunHttpRequestHandler handler) {
        HttpContext context = server.createContext(handler.endpoint());
        context.setHandler(handler);
        log.info("Registered handler: path={}, class={}", handler.endpoint(), handler.getClass().getSimpleName());
    }

    private static void setThreadPool(HttpServer server) {
        int nThreads = Runtime.getRuntime().availableProcessors();
        server.setExecutor(Executors.newFixedThreadPool(nThreads));
        log.info("Server thread pool size is {}", nThreads);
    }

}
