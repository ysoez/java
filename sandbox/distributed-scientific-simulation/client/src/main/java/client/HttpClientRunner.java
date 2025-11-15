package client;

import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
public class HttpClientRunner {

    private static final String WORKER_ADDRESS_1 = "http://localhost:8081/task";
    private static final String WORKER_ADDRESS_2 = "http://localhost:8082/task";

    public static void main(String[] args) {
        var webClient = new WebClient(HttpClient.Version.HTTP_2);
        var aggregator = new Aggregator(webClient);
        String task1 = "100,2000";
        String task2 = "123456789,100000000000000,700000002342343";
        List<String> results = aggregator.sendTasks(asList(WORKER_ADDRESS_1, WORKER_ADDRESS_2), asList(task1, task2));
        for (String result : results) {
            log.info(result);
        }
    }

}
