package library.net.http.client;

import java.net.http.HttpClient;
import java.time.Duration;

class HttpClientSetup {

    public static void main(String[] args) {
        try (var defaultClient = HttpClient.newHttpClient();
             var customClient = HttpClient.newBuilder()
                     .version(HttpClient.Version.HTTP_2)
                     .connectTimeout(Duration.ofSeconds(10))
                     .build()) {
            System.out.printf(
                    "default settings: version=%s, connectTimeout=%s\n",
                    defaultClient.version(),
                    defaultClient.connectTimeout()
            );
            System.out.printf(
                    "custom settings: version=%s, connectTimeout=%s\n",
                    customClient.version(),
                    customClient.connectTimeout()
            );
        }
    }

}
