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

    public CompletableFuture<String> sendTask(String url, byte[] requestPayload, boolean debugEnabled) {
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload))
                .uri(URI.create(url))
                .header("X-Debug", String.valueOf(debugEnabled))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(resp -> {
            System.out.println(resp.headers().allValues("x-debug-info"));
            return resp;
        }).thenApply(HttpResponse::body);
    }

}
