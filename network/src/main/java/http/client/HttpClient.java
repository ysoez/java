package http.client;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public interface HttpClient extends AutoCloseable {

    HttpResponse send(HttpRequest request) throws IOException;

    CompletionStage<HttpResponse> sendAsync(HttpRequest request);

}
