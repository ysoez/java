package reflection.method.polymophism.client;

public class UdpClient {

    public void sendAndForget(String requestPayload) {
        System.out.printf("Request : %s was sent through UDP%n", requestPayload);
    }

}
