package cluster.http.server.engine;

import cluster.http.server.HttpHeader;
import cluster.http.server.HttpMethod;
import cluster.http.server.HttpServer;
import cluster.http.server.HttpTransaction;
import cluster.http.server.handler.HealthCheckRequestHandler;
import cluster.http.server.handler.HttpRequestHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.joining;

@Slf4j
public class SunHttpServer implements HttpServer {

    private final int port;
    private final Set<HttpRequestHandler> handlers;
    private Executor executor;
    private com.sun.net.httpserver.HttpServer server;

    public SunHttpServer(int port) {
        this(port, Runtime.getRuntime().availableProcessors());
    }

    public SunHttpServer(int port, int threadPoolSize) {
        this.port = port;
        this.handlers = new HashSet<>();
        withExecutor(Executors.newFixedThreadPool(threadPoolSize));
    }

    @Override
    public SunHttpServer withHealthCheck() {
        addHandler(new HealthCheckRequestHandler());
        return this;
    }

    @Override
    public HttpServer withExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public SunHttpServer addHandler(HttpRequestHandler handler) {
        handlers.add(handler);
        return this;
    }

    @Override
    public void start() throws Exception {
        this.server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(port), 0);
        registerRequestHandlers();
        this.server.setExecutor(executor);
        this.server.start();
    }

    @Override
    public void stop() {
        server.stop(10);
    }

    private void registerRequestHandlers() {
        for (var handler : handlers) {
            HttpContext context = server.createContext(handler.endpoint());
            context.setHandler(exchange -> {
                if (isMethodNotAllowed(exchange, handler))
                    return;
                handler.handle(new HttpTransaction() {
                    @Override
                    public URI requestUri() {
                        return exchange.getRequestURI();
                    }
                    @Override
                    public Map<String, List<String>> requestHeaders() {
                        return exchange.getRequestHeaders();
                    }
                    @Override
                    public byte[] payload() throws IOException {
                        return exchange.getRequestBody().readAllBytes();
                    }
                    @Override
                    public boolean isModeEnabled(String header) {
                        var headers = requestHeaders();
                        return headers.containsKey(header) && headers.get(header).getFirst().equalsIgnoreCase("true");
                    }
                    @Override
                    public void addResponseHeader(String key, String value) {
                        exchange.getResponseHeaders().add(key, value);
                    }
                    @Override
                    public void putResponseHeader(String key, List<String> value) {
                        exchange.getResponseHeaders().put(key, value);
                    }
                    @Override
                    public void sendOk(byte[] responseBytes) throws IOException {
                        writeResponse(200, responseBytes);
                    }
                    @Override
                    public void sendError(int statusCode, byte[] responseBytes) throws IOException {
                        writeResponse(statusCode, responseBytes);
                    }
                    private void writeResponse(int status, byte[] responseBytes) throws IOException {
                        exchange.sendResponseHeaders(status, responseBytes.length);
                        OutputStream outputStream = exchange.getResponseBody();
                        try {
                            outputStream.write(responseBytes);
                            outputStream.flush();
                        } finally {
                            try {
                                outputStream.close();
                            } finally {
                                exchange.close();
                            }
                        }
                    }
                });
            });
        }
    }

    protected boolean isMethodNotAllowed(HttpExchange exchange, HttpRequestHandler handler) throws IOException {
        if (handler.allowedMethods().contains(HttpMethod.fromString(exchange.getRequestMethod()))) {
            return false;
        }
        var allowedMethods = handler.allowedMethods().stream().map(HttpMethod::name).collect(joining(", "));
        exchange.getResponseHeaders().set(HttpHeader.ALLOW, allowedMethods);
        exchange.sendResponseHeaders(405, -1);
        exchange.close();
        return true;
    }

}
