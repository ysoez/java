package cluster.http.server.handler;

import cluster.http.server.HttpTransaction;

import java.io.IOException;

public class HealthCheckRequestHandler extends AbstractSunHttpRequestHandler {

    @Override
    public String endpoint() {
        return "/status";
    }

    @Override
    public String method() {
        return "get";
    }

    @Override
    public void handle(HttpTransaction transaction) throws IOException {
        String responseMessage = "server is alive\n";
        transaction.sendOk(responseMessage.getBytes());
    }

}
