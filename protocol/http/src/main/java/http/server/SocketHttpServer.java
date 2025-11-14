package http.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static http.Constants.*;

class SocketHttpServer {

    public static void main(String[] args) {
        var cores = Runtime.getRuntime().availableProcessors();
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
             ExecutorService executor = Executors.newFixedThreadPool(cores)) {
            System.out.println("server started on port: " + SERVER_PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.execute(() -> handleRequest(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("server failed to start or accept connections: " + e.getMessage());
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try (clientSocket;
             InputStream is = clientSocket.getInputStream();
             BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            String requestLine = parseRequestLine(in);
            if (requestLine == null) {
                return;
            }
            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];

            if (!METHOD_POST.equals(method)) {
                out.println("HTTP/1.1 405 Method Not Allowed");
                out.println();
                return;
            }

            Map<String, String> headers = parseHeaders(in);
            String body = "";
            if (headers.containsKey("Content-Length")) {
                int contentLength = Integer.parseInt(headers.get("Content-Length"));
                byte[] bodyBytes = new byte[contentLength];
                int bytesRead = 0;
                while (bytesRead < contentLength) {
                    int read = is.read(bodyBytes, bytesRead, contentLength - bytesRead);
                    if (read == -1) {
                        throw new IOException("Unexpected end of stream while reading body");
                    }
                    bytesRead += read;
                }
                body = new String(bodyBytes, StandardCharsets.UTF_8);
                System.out.println("request body: " + body);
            }

            String responseBody;
            if (MESSAGE_PING.equals(body.trim())) {
                responseBody = MESSAGE_PONG;
            } else {
                responseBody = "echo: " + body;
            }

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/plain");
            out.println("Content-Length: " + responseBody.getBytes(StandardCharsets.UTF_8).length);
            out.println();
            out.print(responseBody);
        } catch (IOException | NumberFormatException e) {
            System.err.println("error handling request: " + e.getMessage());
        }
    }

    private static Map<String, String> parseHeaders(BufferedReader in) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String headerLine;
        while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
            int colonIndex = headerLine.indexOf(':');
            if (colonIndex > 0) {
                String key = headerLine.substring(0, colonIndex).trim();
                String value = headerLine.substring(colonIndex + 1).trim();
                headers.put(key, value);
            }
        }
        return headers;
    }

    private static String parseRequestLine(BufferedReader in) throws IOException {
        String requestLine = in.readLine();
        if (requestLine == null) {
            return null;
        }
        System.out.println("request: " + requestLine);
        return requestLine;
    }

}