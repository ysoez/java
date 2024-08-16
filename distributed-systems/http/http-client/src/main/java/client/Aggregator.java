package client;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class Aggregator {

    private final WebClient webClient;

    public List<String> sendTasks(List<String> workersAddresses, List<String> tasks) {
        List<CompletableFuture<String>> futures = new ArrayList<>(workersAddresses.size());
        for (int i = 0; i < workersAddresses.size(); i++) {
            String workerAddress = workersAddresses.get(i);
            String task = tasks.get(i);
            byte[] requestPayload = task.getBytes();
            futures.add(webClient.sendTask(workerAddress, requestPayload));
        }
        return futures.stream().map(CompletableFuture::join).toList();
    }

}
