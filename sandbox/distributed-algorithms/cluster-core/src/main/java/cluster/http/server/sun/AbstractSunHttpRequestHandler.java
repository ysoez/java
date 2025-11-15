package cluster.http.server.sun;

import cluster.http.server.HttpTransaction;
import cluster.http.server.handler.HttpRequestHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

public abstract class AbstractSunHttpRequestHandler implements HttpRequestHandler, HttpHandler {

    protected static final String HEADER_X_DEBUG = "X-Debug";
    protected static final String HEADER_X_TEST = "X-Test";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (isMethodNotAllowed(exchange))
            return;
        handle(new HttpTransaction() {
            @Override
            public URI requestUri() {
                return exchange.getRequestURI();
            }
            @Override
            public Map<String, List<String>> requestHeaders() {
                return exchange.getRequestHeaders();
            }
            @Override
            public byte[] requestPayload() throws IOException {
                return exchange.getRequestBody().readAllBytes();
            }
            @Override
            public void addResponseHeader(String key, String value) {
                exchange.getResponseHeaders().add(key, value);
            }
            @Override
            public void putResponseHeader(String key, List<String> value) {
                exchange.getResponseHeaders().put(key, value);
            }
            @Override
            public void sendOk(byte[] responseBytes) throws IOException {
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                try {
                    outputStream.write(responseBytes);
                    outputStream.flush();
                } finally {
                    try {
                        outputStream.close();
                    } finally {
                        exchange.close();
                    }
                }
            }
        });
    }

    protected boolean isMethodNotAllowed(HttpExchange exchange) {
        if (!exchange.getRequestMethod().equalsIgnoreCase(method())) {
            exchange.close(); // ~ close HTTP transaction
            return true;
        }
        return false;
    }

    protected boolean isModeEnabled(Map<String, List<String>> headers, String xHeader) {
        return headers.containsKey(xHeader) && headers.get(xHeader).getFirst().equalsIgnoreCase("true");
    }

}
