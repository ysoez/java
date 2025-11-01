package cluster.http.server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HealthCheckRequestHandler extends AbstractHttpRequestHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (isMethodNotAllowed(exchange)) {
            return;
        }
        String responseMessage = "Server is alive\n";
        sendOk(responseMessage.getBytes(), exchange);
    }

    @Override
    public String endpoint() {
        return "/status";
    }

    @Override
    public String method() {
        return "get";
    }
}
