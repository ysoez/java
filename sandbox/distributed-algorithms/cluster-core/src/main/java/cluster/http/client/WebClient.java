package cluster.http.client;

import java.util.concurrent.CompletableFuture;

public interface WebClient {

    <T> CompletableFuture<T> sendRequest(String url, byte[] requestPayload);

    CompletableFuture<byte[]> sendTask(String url, byte[] requestPayload);

}
