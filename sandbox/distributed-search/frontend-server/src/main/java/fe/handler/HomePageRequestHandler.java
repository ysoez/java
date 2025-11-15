package fe.handler;

import cluster.http.server.HttpTransaction;
import cluster.http.server.sun.AbstractSunHttpRequestHandler;

import java.io.IOException;
import java.io.InputStream;

public class HomePageRequestHandler extends AbstractSunHttpRequestHandler {

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
    public void handle(HttpTransaction transaction) throws IOException {
        byte[] response;
        String path = transaction.requestUri().getPath();
        if (path.equals(endpoint())) {
            response = readUiAsset(HOME_PAGE_PATH);
        } else {
            response = readUiAsset(path);
        }
        addContentType(path, transaction);
        transaction.sendOk(response);
    }

    private byte[] readUiAsset(String asset) throws IOException {
        InputStream assetStream = getClass().getResourceAsStream(asset);
        if (assetStream == null) {
            return new byte[]{};
        }
        return assetStream.readAllBytes();
    }

    private static void addContentType(String asset, HttpTransaction transaction) {
        String contentType = "text/html";
        if (asset.endsWith("js")) {
            contentType = "text/javascript";
        } else if (asset.endsWith("css")) {
            contentType = "text/css";
        }
        transaction.addResponseHeader("Content-Type", contentType);
    }
}
