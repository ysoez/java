package server;

import cluster.http.server.HttpTransaction;
import cluster.http.server.handler.AbstractSunHttpRequestHandler;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;

public class NumbersMultiplierHttpRequestHandler extends AbstractSunHttpRequestHandler {

    @Override
    public String endpoint() {
        return "/task";
    }

    @Override
    public String method() {
        return "POST";
    }

    @Override
    public void handle(HttpTransaction exchange) throws IOException {
        var headers = exchange.requestHeaders();
        if (isModeEnabled(headers, HEADER_X_TEST)) {
            String dummyResponse = "123\n";
            exchange.sendOk(dummyResponse.getBytes());
            return;
        }
        boolean isDebugMode = false;
        if (isModeEnabled(headers, HEADER_X_DEBUG)) {
            isDebugMode = true;
        }
        byte[] responseBytes = processRequest(exchange, isDebugMode);
        exchange.sendOk(responseBytes);
    }

    private byte[] processRequest(HttpTransaction exchange, boolean isDebugMode) throws IOException {
        long startTime = System.nanoTime();
        byte[] requestBytes = exchange.requestPayload();
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
