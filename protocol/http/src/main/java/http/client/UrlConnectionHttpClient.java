package http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

import static http.Constants.METHOD_POST;
import static http.Constants.SERVER_PORT;

public class UrlConnectionHttpClient {

    public static void main(String[] args) throws URISyntaxException {
        String urlString = "http://localhost:" + SERVER_PORT;
        try {
            var url = new URI(urlString).toURL();
            var connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(METHOD_POST);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println("response body: " + response);
            } else {
                System.out.println("request failed: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("failed to send request: " + e.getMessage());
        }
    }
}