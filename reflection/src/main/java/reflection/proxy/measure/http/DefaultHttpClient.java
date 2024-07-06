package reflection.proxy.measure.http;

public final class DefaultHttpClient implements HttpClient {

    @Override
    public void initialize() {
        System.out.println("HTTP: Initializing HTTP client");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sendRequest(String request) {
        System.out.printf("HTTP: Sending request [%s]%n", request);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("HTTP: Response 200 OK");
        return "data";
    }
}
