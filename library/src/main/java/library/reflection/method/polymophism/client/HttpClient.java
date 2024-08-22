package library.reflection.method.polymophism.client;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpClient {

    private final String serverIp;

    public boolean sendRequest(String data) {
        System.out.printf("Request with body : %s was successfully sent to server with address : %s%n", data, serverIp);
        return true;
    }

}
