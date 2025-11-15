package server;

import cluster.http.server.HttpTransaction;
import cluster.http.server.sun.AbstractSunHttpRequestHandler;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class NumbersMultiplierRequestHandler extends AbstractSunHttpRequestHandler {

    @Override
    public String endpoint() {
        return "/task";
    }

    @Override
    public String method() {
        return "POST";
    }

    @Override
    public void handle(HttpTransaction transaction) throws IOException {
        var headers = transaction.requestHeaders();
        if (isModeEnabled(headers, HEADER_X_TEST)) {
            String dummyResponse = "123\n";
            transaction.sendOk(dummyResponse.getBytes());
            return;
        }
        boolean isDebugMode = isModeEnabled(headers, HEADER_X_DEBUG);
        byte[] responseBytes = processRequest(transaction, isDebugMode);
        transaction.sendOk(responseBytes);
    }

    private byte[] processRequest(HttpTransaction transaction, boolean isDebugMode) throws IOException {
        long startTime = System.nanoTime();
        byte[] requestBytes = transaction.requestPayload();
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
