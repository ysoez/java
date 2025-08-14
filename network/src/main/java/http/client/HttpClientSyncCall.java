package http.client;

import http.HttpMethod;
import http.HttpRequest;
import http.client.apache.ApacheSyncHttpClient;

class HttpClientSyncCall {

    public static void main(String[] args) throws Exception {
        try (var client = new ApacheSyncHttpClient()) {
            var request = HttpRequest.builder()
                    .url("https://httpbin.org/get")
                    .method(HttpMethod.GET)
                    .build();
            var response = client.send(request);
            System.out.println(response);
        }
    }

}
