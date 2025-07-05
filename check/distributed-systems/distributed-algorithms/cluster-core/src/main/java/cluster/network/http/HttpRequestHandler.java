package cluster.network.http;

import cluster.network.RequestHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public interface HttpRequestHandler extends HttpHandler, RequestHandler<HttpExchange> {
}