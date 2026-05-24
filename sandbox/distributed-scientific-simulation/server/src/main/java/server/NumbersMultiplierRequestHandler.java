package server;

import cluster.http.server.HttpHeader;
import cluster.http.server.HttpMethod;
import cluster.http.server.HttpTransaction;
import cluster.http.server.handler.HttpRequestHandler;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;

public class NumbersMultiplierRequestHandler implements HttpRequestHandler {

    @Override
    public String endpoint() {
        return "/task";
    }

    @Override
    public EnumSet<HttpMethod> allowedMethods() {
        return EnumSet.of(HttpMethod.POST);
    }

    @Override
    public void handle(HttpTransaction http) throws IOException {
        if (http.isModeEnabled(HttpHeader.X_TEST)) {
            String dummyResponse = "123\n";
            http.sendOk(dummyResponse.getBytes());
            return;
        }
        boolean isDebugMode = http.isModeEnabled(HttpHeader.X_DEBUG);
        byte[] responseBytes = processRequest(http, isDebugMode);
        http.sendOk(responseBytes);
    }

    private byte[] processRequest(HttpTransaction transaction, boolean isDebugMode) throws IOException {
        long startTime = System.nanoTime();
        byte[] requestBytes = transaction.payload();
        byte[] responseBytes = calculateResult(requestBytes);
        long finishTime = System.nanoTime();
        if (isDebugMode) {
            String debugMessage = String.format("operation took %d ns", finishTime - startTime);
            transaction.putResponseHeader("X-Debug-Info", Collections.singletonList(debugMessage));
        }
        return responseBytes;
    }

    private byte[] calculateResult(byte[] requestBytes) {
        var bodyString = new String(requestBytes);
        String[] stringNumbers = bodyString.split(",");
        System.out.println("numbers to multiply: " + Arrays.toString(stringNumbers));

        var result = BigInteger.ONE;
        for (String number : stringNumbers) {
            var bigInteger = new BigInteger(number);
            result = result.multiply(bigInteger);
        }

        return String.format("multiplication result: %s\n", result).getBytes();
    }

}
