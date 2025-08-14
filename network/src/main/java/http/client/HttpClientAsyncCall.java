package http.client;

import http.HttpMethod;
import http.HttpRequest;
import http.client.okhttp.OkHttpAsyncHttpClient;

class HttpClientAsyncCall {

    public static void main(String[] args) throws Exception {
        try (var client = new OkHttpAsyncHttpClient()) {
            var request = HttpRequest.builder()
                    .url("https://httpbin.org/get")
                    .method(HttpMethod.GET)
                    .build();
            client.sendAsync(request).whenComplete((res, err) -> {
                if (err != null) {
                    System.err.println(err);
                    return;
                }
                System.out.println(res);
            });
//            System.out.println(response);
        }
    }

}
