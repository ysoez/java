package cluster.http.server.handler;

import cluster.http.server.HttpTransaction;

import java.io.IOException;

public interface HttpRequestHandler {

    String endpoint();

    String method();

    void handle(HttpTransaction transaction) throws IOException;

}