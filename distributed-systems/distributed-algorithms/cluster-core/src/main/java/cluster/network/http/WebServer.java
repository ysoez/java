package cluster.network.http;

import cluster.network.RequestHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

public class WebServer {

    private final int port;
    private final int threadPoolSize;
    private final Set<RequestHandler<HttpExchange>> requestHandlers;
    private HttpServer server;

    public WebServer(int port) {
        this.port = port;
        this.threadPoolSize = 8;
        this.requestHandlers = new HashSet<>();
    }

    public WebServer(int port, int threadPoolSize) {
        this.port = port;
        this.threadPoolSize = threadPoolSize;
        this.requestHandlers = new HashSet<>();
    }

    public WebServer withHealthCheck() {
        addHandler(new HealthCheckRequestHandler());
        return this;
    }

    public WebServer addHandler(RequestHandler<HttpExchange> handler) {
        requestHandlers.add(handler);
        return this;
    }

    public void startServer() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        registerRequestHandlers();
        server.setExecutor(Executors.newFixedThreadPool(threadPoolSize));
        server.start();
    }

    public void stop() {
        server.stop(10);
    }

    private void registerRequestHandlers() {
        for (var requestHandler : requestHandlers) {
            HttpContext context = server.createContext(requestHandler.endpoint());
            context.setHandler(requestHandler::handle);
        }
    }
}
