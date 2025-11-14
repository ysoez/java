package cluster.http.server;

import cluster.http.server.handler.HttpRequestHandler;

import java.util.concurrent.Executor;

public interface WebServer<H extends HttpRequestHandler> {

    WebServer<H> withHealthCheck();

    WebServer<H> withExecutor(Executor executor);

    WebServer<H> addHandler(H handler);

    void start();

    void stop();

}
