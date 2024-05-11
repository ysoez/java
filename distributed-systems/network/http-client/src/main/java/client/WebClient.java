package client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class WebClient {

    private final HttpClient client;

    public WebClient(HttpClient.Version version) {
        this.client = HttpClient.newBuilder()
                .version(version)
                .build();
    }

    public CompletableFuture<String> sendTask(String url, byte[] requestPayload) {
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload))
                .uri(URI.create(url))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }

}
