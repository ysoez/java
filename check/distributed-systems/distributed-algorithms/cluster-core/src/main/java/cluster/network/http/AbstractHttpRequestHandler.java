package cluster.network.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractHttpRequestHandler implements HttpRequestHandler {

    protected static final String X_DEBUG_HEADER = "X-Debug";
    protected static final String X_TEST_HEADER = "X-Test";

    protected boolean isHttpMethodNotAllowed(HttpExchange exchange, String expectedMethod) {
        if (!exchange.getRequestMethod().equalsIgnoreCase(expectedMethod)) {
            exchange.close(); // ~ close HTTP transaction
            return true;
        }
        return false;
    }

    protected void sendOk(byte[] responseBytes, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, responseBytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.flush();
        outputStream.close();
        exchange.close();
    }

}
