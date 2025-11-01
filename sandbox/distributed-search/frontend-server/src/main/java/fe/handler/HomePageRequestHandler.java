package fe.handler;

import cluster.http.server.handler.AbstractHttpRequestHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;

public class HomePageRequestHandler extends AbstractHttpRequestHandler {

    private static final String ASSETS_BASE_DIR = "/assets/";
    private static final String HOME_PAGE_PATH = "/assets/index.html";

    @Override
    public String endpoint() {
        return "/";
    }

    @Override
    public String method() {
        return "get";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (isMethodNotAllowed(exchange)) {
            return;
        }
        byte[] response;
        String path = exchange.getRequestURI().getPath();
        if (path.equals(endpoint())) {
            response = readUiAsset(HOME_PAGE_PATH);
        } else {
            response = readUiAsset(path);
        }
        addContentType(path, exchange);
        sendOk(response, exchange);
    }

    private byte[] readUiAsset(String asset) throws IOException {
        InputStream assetStream = getClass().getResourceAsStream(asset);
        if (assetStream == null) {
            return new byte[]{};
        }
        return assetStream.readAllBytes();
    }

    private static void addContentType(String asset, HttpExchange exchange) {
        String contentType = "text/html";
        if (asset.endsWith("js")) {
            contentType = "text/javascript";
        } else if (asset.endsWith("css")) {
            contentType = "text/css";
        }
        exchange.getResponseHeaders().add("Content-Type", contentType);
    }
}
