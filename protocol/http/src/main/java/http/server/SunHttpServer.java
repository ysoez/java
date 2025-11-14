package http.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import static http.Constants.*;

class SunHttpServer {

    public static void main(String[] args) throws IOException {
        var address = new InetSocketAddress(SERVER_PORT);
        var server = HttpServer.create(address, 0);
        server.createContext("/", exchange -> {
            try {
                String method = exchange.getRequestMethod();
                if (!METHOD_POST.equals(method)) {
                    exchange.sendResponseHeaders(405, 0);
                    return;
                }

                InputStream is = exchange.getRequestBody();
                byte[] bodyBytes = is.readAllBytes();
                String body = new String(bodyBytes, StandardCharsets.UTF_8).trim();
                System.out.println("request body: " + body);

                String responseBody;
                if (MESSAGE_PING.equals(body)) {
                    responseBody = MESSAGE_PONG;
                } else {
                    responseBody = "echo: " + body;
                }

                exchange.getResponseHeaders().set("Content-Type", "text/plain");
                exchange.sendResponseHeaders(200, responseBody.getBytes(StandardCharsets.UTF_8).length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBody.getBytes(StandardCharsets.UTF_8));
                }
            } catch (IOException e) {
                System.err.println("error handling request: " + e.getMessage());
                exchange.sendResponseHeaders(500, 0);
            } finally {
                exchange.close();
            }
        });
        server.setExecutor(null); // ~ default single-threaded executor
        server.start();
        System.out.println("server started on port: " + SERVER_PORT);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.stop(0);
            System.out.println("server stopped");
        }));
    }

}