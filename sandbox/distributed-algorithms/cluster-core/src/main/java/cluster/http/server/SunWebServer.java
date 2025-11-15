package cluster.http.server;

import cluster.http.server.handler.AbstractSunHttpRequestHandler;
import cluster.http.server.handler.HealthCheckRequestHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SunWebServer implements WebServer<AbstractSunHttpRequestHandler> {

    private final int port;
    private final Set<AbstractSunHttpRequestHandler> requestHandlers;
    private Executor executor;
    private HttpServer server;

    public SunWebServer(int port) {
        this(port, Runtime.getRuntime().availableProcessors());
    }

    public SunWebServer(int port, int threadPoolSize) {
        this.port = port;
        this.requestHandlers = new HashSet<>();
        withExecutor(Executors.newFixedThreadPool(threadPoolSize));
    }

    @Override
    public SunWebServer withHealthCheck() {
        addHandler(new HealthCheckRequestHandler());
        return this;
    }

    @Override
    public WebServer<AbstractSunHttpRequestHandler> withExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public SunWebServer addHandler(AbstractSunHttpRequestHandler handler) {
        requestHandlers.add(handler);
        return this;
    }

    @Override
    public void start() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        registerRequestHandlers();
        this.server.setExecutor(executor);
        this.server.start();
    }

    @Override
    public void stop() {
        server.stop(10);
    }

    private void registerRequestHandlers() {
        for (var requestHandler : requestHandlers) {
            HttpContext context = server.createContext(requestHandler.endpoint());
            context.setHandler(requestHandler);
        }
    }
}
