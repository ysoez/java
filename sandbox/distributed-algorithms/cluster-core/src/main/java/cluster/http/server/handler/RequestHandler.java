package cluster.http.server.handler;

import java.io.IOException;

public interface RequestHandler<T> {

    String endpoint();

    void handle(T payload) throws IOException;

}