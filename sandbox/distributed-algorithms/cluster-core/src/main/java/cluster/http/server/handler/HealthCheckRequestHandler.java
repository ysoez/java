package cluster.http.server.handler;

import cluster.http.server.HttpMethod;
import cluster.http.server.HttpTransaction;

import java.io.IOException;
import java.util.EnumSet;

public class HealthCheckRequestHandler implements HttpRequestHandler {

    @Override
    public String endpoint() {
        return "/status";
    }

    @Override
    public EnumSet<HttpMethod> allowedMethods() {
        return EnumSet.of(HttpMethod.GET);
    }

    @Override
    public void handle(HttpTransaction transaction) throws IOException {
        String responseMessage = "server is alive\n";
        transaction.sendOk(responseMessage.getBytes());
    }

}
