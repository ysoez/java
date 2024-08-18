package server.handler;

import cluster.network.http.AbstractHttpRequestHandler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;

public class NumbersMultiplierHttpRequestHandler extends AbstractHttpRequestHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (isHttpMethodNotAllowed(exchange, "post")) {
            return;
        }
        Headers headers = exchange.getRequestHeaders();
        if (isModeEnabled(headers, X_TEST_HEADER)) {
            String dummyResponse = "123\n";
            sendOk(dummyResponse.getBytes(), exchange);
            return;
        }
        boolean isDebugMode = false;
        if (isModeEnabled(headers, X_DEBUG_HEADER)) {
            isDebugMode = true;
        }
        byte[] responseBytes = processRequest(exchange, isDebugMode);
        sendOk(responseBytes, exchange);
    }

    @Override
    public String endpoint() {
        return "/task";
    }

    private static boolean isModeEnabled(Headers headers, String xHeader) {
        return headers.containsKey(xHeader) && headers.get(xHeader).getFirst().equalsIgnoreCase("true");
    }

    private byte[] processRequest(HttpExchange exchange, boolean isDebugMode) throws IOException {
        long startTime = System.nanoTime();
        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        byte[] responseBytes = calculateResult(requestBytes);
        long finishTime = System.nanoTime();
        if (isDebugMode) {
            String debugMessage = String.format("Operation took %d ns", finishTime - startTime);
            exchange.getResponseHeaders().put("X-Debug-Info", Collections.singletonList(debugMessage));
        }
        return responseBytes;
    }

    private byte[] calculateResult(byte[] requestBytes) {
        String bodyString = new String(requestBytes);
        String[] stringNumbers = bodyString.split(",");

        BigInteger result = BigInteger.ONE;
        for (String number : stringNumbers) {
            BigInteger bigInteger = new BigInteger(number);
            result = result.multiply(bigInteger);
        }

        return String.format("Result of the multiplication is %s\n", result).getBytes();
    }

}
