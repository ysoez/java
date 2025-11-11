package cluster.http.server;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface HttpTransaction {

    URI requestUri();

    Map<String, List<String>> requestHeaders();

    byte[] requestPayload() throws IOException;

    void addResponseHeader(String key, String value);

    void sendOk(byte[] responseBytes) throws IOException;

}
