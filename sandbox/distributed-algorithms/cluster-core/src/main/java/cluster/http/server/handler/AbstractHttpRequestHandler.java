package cluster.http.server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractHttpRequestHandler implements HttpRequestHandler {

    protected static final String HEADER_X_DEBUG = "X-Debug";
    protected static final String HEADER_X_TEST = "X-Test";

    protected boolean isMethodNotAllowed(HttpExchange exchange) {
        if (!exchange.getRequestMethod().equalsIgnoreCase(method())) {
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
