package cluster.http.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public interface HttpRequestHandler extends HttpHandler, RequestHandler<HttpExchange> {

    String method();

}