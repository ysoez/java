package cluster.http.server;

import cluster.http.server.handler.HttpRequestHandler;

public interface WebServer<H extends HttpRequestHandler> {

    WebServer<H> withHealthCheck();

    WebServer<H> addHandler(H handler);

    void start();

    void stop();

}
