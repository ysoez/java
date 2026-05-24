package cluster.http.client;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class JdkHttpClient implements HttpClient {

    private final java.net.http.HttpClient client;

    public JdkHttpClient(java.net.http.HttpClient.Version version) {
        this.client = java.net.http.HttpClient.newBuilder()
                .version(version)
                .build();
    }

    public JdkHttpClient() {
        this(java.net.http.HttpClient.Version.HTTP_1_1);
    }

    @Override
    public CompletableFuture<byte[]> sendRequest(String url, byte[] payload) {
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(payload))
                .uri(URI.create(url))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                .thenApply(HttpResponse::body);
    }

}
