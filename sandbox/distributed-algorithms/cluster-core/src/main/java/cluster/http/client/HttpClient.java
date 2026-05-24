package cluster.http.client;

import java.util.concurrent.CompletableFuture;

public interface HttpClient {

    CompletableFuture<byte[]> sendRequest(String url, byte[] payload);

}
