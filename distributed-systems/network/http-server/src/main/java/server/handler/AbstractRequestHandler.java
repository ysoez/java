package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractRequestHandler implements HttpHandler {

    static final String X_DEBUG_HEADER = "X-Debug";
    static final String X_TEST_HEADER = "X-Test";


    protected boolean isHttpMethodAllowed(HttpExchange exchange, String expectedMethod) {
        if (!exchange.getRequestMethod().equalsIgnoreCase(expectedMethod)) {
            exchange.close(); // ~ close HTTP transaction
            return false;
        }
        return true;
    }

    protected void sendResponse(byte[] responseBytes, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }

    public abstract String endpoint();

}
