package cluster.http.server.handler;

import cluster.http.server.HttpMethod;
import cluster.http.server.HttpTransaction;

import java.io.IOException;
import java.util.EnumSet;

public interface HttpRequestHandler {

    String endpoint();

    EnumSet<HttpMethod> allowedMethods();

    void handle(HttpTransaction transaction) throws IOException;

}