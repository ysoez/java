package server.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;

public class NumbersMultiplierRequestHandler extends AbstractRequestHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!isHttpMethodAllowed(exchange, "post")) {
            return;
        }

        Headers headers = exchange.getRequestHeaders();

        // ~ test mode
        if (headers.containsKey(X_TEST_HEADER) && headers.get(X_TEST_HEADER).getFirst().equalsIgnoreCase("true")) {
            String dummyResponse = "123\n";
            sendResponse(dummyResponse.getBytes(), exchange);
            return;
        }

        // ~ debug mode
        boolean isDebugMode = false;
        if (headers.containsKey(X_DEBUG_HEADER) && headers.get(X_DEBUG_HEADER).getFirst().equalsIgnoreCase("true")) {
            isDebugMode = true;
        }

        // ~ process request
        long startTime = System.nanoTime();
        byte[] requestBytes = exchange.getRequestBody().readAllBytes();
        byte[] responseBytes = calculateResponse(requestBytes);
        long finishTime = System.nanoTime();

        if (isDebugMode) {
            String debugMessage = String.format("Operation took %d ns", finishTime - startTime);
            exchange.getResponseHeaders().put("X-Debug-Info", Collections.singletonList(debugMessage));
        }

        sendResponse(responseBytes, exchange);
    }

    private byte[] calculateResponse(byte[] requestBytes) {
        String bodyString = new String(requestBytes);
        String[] stringNumbers = bodyString.split(",");

        BigInteger result = BigInteger.ONE;
        for (String number : stringNumbers) {
            BigInteger bigInteger = new BigInteger(number);
            result = result.multiply(bigInteger);
        }

        return String.format("Result of the multiplication is %s\n", result).getBytes();
    }

    @Override
    public String endpoint() {
        return "/task";
    }

}
