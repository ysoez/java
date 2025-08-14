package http;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Map;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class HttpResponse {
    private int statusCode;
    private Map<String, String> headers;
    private String body;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP Response:\n");
        sb.append("Status Code: ").append(statusCode).append("\n");
        sb.append("Headers:\n");
        headers.forEach((key, value) -> sb.append("  ").append(key).append(": ").append(value).append("\n"));
        sb.append("Body:\n");
        sb.append(indent(body, "  "));
        return sb.toString();
    }

    private String indent(String text, String prefix) {
        return text == null ? "" : text.lines().map(line -> prefix + line).collect(Collectors.joining("\n"));
    }

}
