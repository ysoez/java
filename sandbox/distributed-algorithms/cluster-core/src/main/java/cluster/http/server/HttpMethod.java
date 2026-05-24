package cluster.http.server;

public enum HttpMethod {
    GET,
    POST;

    public static HttpMethod fromString(String method) {
        return HttpMethod.valueOf(method.toUpperCase());
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
