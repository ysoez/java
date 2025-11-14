package http.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static http.Constants.MESSAGE_PING;
import static http.Constants.SERVER_PORT;

public class Java11HttpClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        String urlString = "http://localhost:" + SERVER_PORT;
        try (var client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(5))
                .build()) {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .timeout(Duration.ofSeconds(5))
                    .header("Content-Type", "text/plain")
                    .POST(HttpRequest.BodyPublishers.ofString(MESSAGE_PING))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("response Code: " + response.statusCode());
            System.out.println("response Headers: " + response.headers().map());
            System.out.println("response Body: " + response.body());
        }
    }

}