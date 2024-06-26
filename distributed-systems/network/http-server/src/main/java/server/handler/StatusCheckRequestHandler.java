package server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class StatusCheckRequestHandler extends AbstractRequestHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!isHttpMethodAllowed(exchange, "get")) {
            return;
        }
        String responseMessage = "Server is alive\n";
        sendResponse(responseMessage.getBytes(), exchange);
    }

    @Override
    public String endpoint() {
        return "/status";
    }

}
