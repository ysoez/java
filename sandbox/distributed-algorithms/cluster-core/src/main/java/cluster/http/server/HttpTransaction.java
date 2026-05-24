package cluster.http.server;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface HttpTransaction {

    URI requestUri();

    Map<String, List<String>> requestHeaders();

    byte[] payload() throws IOException;

    boolean isModeEnabled(String header);

    void addResponseHeader(String key, String value);

    void putResponseHeader(String key, List<String> value);

    void sendOk(byte[] responseBytes) throws IOException;

    void sendError(int statusCode, byte[] responseBytes) throws IOException;

}
