package server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import server.handler.AbstractRequestHandler;
import server.handler.NumbersMultiplierRequestHandler;
import server.handler.StatusCheckRequestHandler;

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
        webServer.startServer();

        log.debug("Server is listening on port {}", serverPort);
    }

    public WebServer(int port) {
        this.port = port;
    }

    public void startServer() throws IOException {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            log.error("Failed to start a server", e);
            throw e;
        }

        registerEndpoint(server, new StatusCheckRequestHandler());
        registerEndpoint(server, new NumbersMultiplierRequestHandler());

        int nThreads = Runtime.getRuntime().availableProcessors();
        server.setExecutor(Executors.newFixedThreadPool(nThreads));
        log.debug("Server thread pool size is {}", nThreads);

        server.start();
    }

    private void registerEndpoint(HttpServer server, AbstractRequestHandler handler) {
        HttpContext context = server.createContext(handler.endpoint());
        context.setHandler(handler);
        log.debug("Registered handler: path={}, class={}", handler.endpoint(), handler.getClass().getSimpleName());
    }

}
