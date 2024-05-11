package client;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.List;

public class Application {

    private static final String WORKER_ADDRESS_1 = "http://localhost:8081/task";
    private static final String WORKER_ADDRESS_2 = "http://localhost:8082/task";

    public static void main(String[] args) {
        var webClient = new WebClient(HttpClient.Version.HTTP_1_1);
        var aggregator = new Aggregator(webClient);
        String task1 = "10,200";
        String task2 = "123456789,100000000000000,700000002342343";
        List<String> results = aggregator.sendTasks(Arrays.asList(WORKER_ADDRESS_1, WORKER_ADDRESS_2), Arrays.asList(task1, task2));
        for (String result : results) {
            System.out.println(result);
        }
    }

}
