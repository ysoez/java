package cluster.http.server;

import cluster.http.server.handler.HttpRequestHandler;

import java.util.concurrent.Executor;

public interface HttpServer {

    HttpServer withHealthCheck();

    HttpServer withExecutor(Executor executor);

    HttpServer addHandler(HttpRequestHandler handler);

    void start() throws Exception;

    void stop();

}
