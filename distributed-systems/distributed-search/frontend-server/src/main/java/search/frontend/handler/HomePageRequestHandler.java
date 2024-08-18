package search.frontend.handler;

import cluster.network.http.AbstractHttpRequestHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;

public class HomePageRequestHandler extends AbstractHttpRequestHandler {

    private static final String HOME_PAGE_UI_ASSETS_BASE_DIR = "/assets/";

    @Override
    public String endpoint() {
        return "/";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (isHttpMethodNotAllowed(exchange, "get")) {
            return;
        }
        byte[] response;
        String path = exchange.getRequestURI().getPath();
        if (path.equals(endpoint())) {
            response = readUiAsset(HOME_PAGE_UI_ASSETS_BASE_DIR + "index.html");
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
