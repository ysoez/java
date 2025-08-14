package http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class HttpRequest {
    private String url;
    private HttpMethod method;
    private Map<String, String> headers;
    private byte[] body;

    public Map<String, String> getHeaders() {
        return headers != null ? headers : new HashMap<>();
    }

}
