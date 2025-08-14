package http.client.okhttp;

import http.HttpRequest;
import http.HttpResponse;
import http.client.HttpClient;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class OkHttpAsyncHttpClient implements HttpClient {

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public HttpResponse send(HttpRequest request) {
        return null;
    }

    @Override
    public CompletionStage<HttpResponse> sendAsync(HttpRequest request) {
        Request.Builder builder = new Request.Builder()
                .url(request.getUrl())
                .method(request.getMethod().name(), request.getBody() != null ? RequestBody.create(request.getBody(),
                        MediaType.parse("application/json")) : null);
        request.getHeaders().forEach(builder::addHeader);
        Request okHttpRequest = builder.build();
        CompletableFuture<HttpResponse> future = new CompletableFuture<>();
        client.newCall(okHttpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Map<String, String> headers = new HashMap<>();
                response.headers().forEach(h -> headers.put(h.getFirst(), h.getSecond()));
                future.complete(new HttpResponse(response.code(), headers, response.body().string()));
            }
        });
        return future;
    }

    @Override
    public void close() throws Exception {
    }
}
