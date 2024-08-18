package server;

import cluster.network.http.AbstractHttpRequestHandler;
import cluster.network.http.HealthCheckRequestHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import server.handler.NumbersMultiplierHttpRequestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

@Slf4j
public class WebServer {

    private final int port;

    public static void main(String[] args) throws IOException {
        int serverPort = 8080;
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
        }
        var webServer = new WebServer(serverPort);
        webServer.start();
        log.info("Server is listening on port {}", serverPort);
    }

    public WebServer(int port) {
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

    private void registerEndpoint(HttpServer server, AbstractHttpRequestHandler handler) {
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
