package http.client.apache;

import http.HttpRequest;
import http.HttpResponse;
import http.client.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

public class ApacheSyncHttpClient implements HttpClient {

    private final CloseableHttpClient client;

    public ApacheSyncHttpClient() {
        this.client = HttpClients.createDefault();
    }

    @Override
    public HttpResponse send(HttpRequest request) throws IOException {
        HttpUriRequestBase httpRequest = new HttpUriRequestBase(request.getMethod().name(), URI.create(request.getUrl()));
        request.getHeaders().forEach(httpRequest::addHeader);

        if (request.getBody() != null && request.getBody().length > 0) {
            httpRequest.setEntity(new ByteArrayEntity(request.getBody(), 0, request.getBody().length, ContentType.APPLICATION_JSON));
        }

        try (var response = client.execute(httpRequest)) {
            var entity = response.getEntity();
            String body = entity != null ? new String(entity.getContent().readAllBytes()) : null;

            Map<String, String> headers = new HashMap<>();
            for (Header header : response.getHeaders()) {
                headers.put(header.getName(), header.getValue());
            }

            return new HttpResponse(response.getCode(), headers, body);
        }
    }

    @Override
    public CompletionStage<HttpResponse> sendAsync(HttpRequest request) {
        return null;
    }

    @Override
    public void close() throws Exception {
        client.close();
    }

}
