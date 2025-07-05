package cluster.network;

import java.io.IOException;

public interface RequestHandler<T> {

    String endpoint();

    void handle(T requestPayload) throws IOException;

}