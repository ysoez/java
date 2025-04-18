package cluster.network.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HealthCheckRequestHandler extends AbstractHttpRequestHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (isHttpMethodNotAllowed(exchange, "get")) {
            return;
        }
        String responseMessage = "Server is alive\n";
        sendOk(responseMessage.getBytes(), exchange);
    }

    @Override
    public String endpoint() {
        return "/status";
    }

}
