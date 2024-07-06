package reflection.proxy.measure.http;

public interface HttpClient {

    void initialize();

    String sendRequest(String request);

}
