package library.concurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

import static java.lang.Runtime.getRuntime;
import static java.util.stream.Collectors.groupingBy;
import static library.concurrency.ConcurrencyUtils.*;

class ConcurrentRequestHandling {

    public static void main(String[] args) throws Exception {
        List<Request> requests = new ArrayList<>();
        Map<String, Integer> taskCountPerUser = new HashMap<>();
        for (int i = 1; i <= 50; i++) {
            String userId = "user#" + (i % 5);
            var taskCount = taskCountPerUser.getOrDefault(userId, 0);
            taskCountPerUser.put(userId, ++taskCount);
            requests.add(new Request(userId, "request#" + taskCount));
        }
        //
        // ~ handle requests concurrently among users but sequentially for the same user
        //
        try (RequestProcessor processor = new QueuePerUserRequestProcessor(getRuntime().availableProcessors())) {
            processor.process(requests);
        }
    }

    private record Request(String userId, String data) {}

    private static class RequestHandler implements Consumer<Request> {
        @Override
        public void accept(Request request) {
            var latency = ThreadLocalRandom.current().nextLong(TimeUnit.SECONDS.toMillis(3));
            sleep(latency, TimeUnit.MILLISECONDS);
            LockSupport.parkNanos(latency);
            ConcurrencyUtils.logWorker("took " + latency + "ms for " + request);
        }
    }

    private interface RequestProcessor extends AutoCloseable {
        void process(List<Request> requests);
    }

    private static class ThreadPerUserRequestProcessor implements RequestProcessor {

        private final ExecutorService globalExecutor;
        private final Map<String, ExecutorService> userExecutors;
        private final RequestHandler requestHandler;

        public ThreadPerUserRequestProcessor(int maxParallelism) {
            globalExecutor = Executors.newFixedThreadPool(maxParallelism);
            userExecutors = new ConcurrentHashMap<>();
            requestHandler = new RequestHandler();
        }

        @Override
        public void process(List<Request> requests) {
            Map<String, List<Request>> requestsByUser = requests.stream().collect(groupingBy(Request::userId));
            for (Map.Entry<String, List<Request>> entry : requestsByUser.entrySet()) {
                String userId = entry.getKey();
                List<Request> userRequests = entry.getValue();
                // ~ bind thread per user to ensure sequential execution
                ExecutorService userExecutor = userExecutors.computeIfAbsent(userId, _ -> Executors.newSingleThreadExecutor());
                globalExecutor.submit(() -> {
                    for (Request request : userRequests) {
                        userExecutor.submit(() -> requestHandler.accept(request));
                    }
                });
            }
        }

        @Override
        public void close() {
            globalExecutor.shutdown();
            userExecutors.values().forEach(ExecutorService::shutdown);
            try {
                if (!globalExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                    globalExecutor.shutdownNow();
                }
                for (ExecutorService executor : userExecutors.values()) {
                    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static class QueuePerUserRequestProcessor implements RequestProcessor {

        private final ExecutorService workerPool;
        private final Map<String, BlockingQueue<Request>> userTaskQueues;
        private final Map<String, Boolean> userProcessingFlags;
        private final RequestHandler requestHandler;

        public QueuePerUserRequestProcessor(int maxParallelism) {
            this.workerPool = Executors.newFixedThreadPool(maxParallelism);
            this.userTaskQueues = new ConcurrentHashMap<>();
            this.userProcessingFlags = new ConcurrentHashMap<>();
            this.requestHandler = new RequestHandler();
        }

        @Override
        public void process(List<Request> requests) {
            for (Request request : requests) {
                userTaskQueues.computeIfAbsent(request.userId(), key -> new LinkedBlockingQueue<>()).add(request);
                userProcessingFlags.compute(request.userId(), (userId, isProcessing) -> {
                    if (isProcessing == null || !isProcessing) {
                        scheduleProcessing(request.userId());
                        return true;
                    }
                    // ~ already being processed
                    return isProcessing;
                });
            }
        }

        @Override
        public void close() throws Exception {
            workerPool.shutdown();
            try {
                logYellow("await termination for 60 sec");
                if (!workerPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    List<Runnable> droppedTasks = workerPool.shutdownNow();
                    logRed("timeout elapsed, " + droppedTasks.size() + " request were not processed");
                }
                logYellow("pool is closed, all task has been executed");
            } catch (InterruptedException e) {
                workerPool.shutdownNow();
            }
        }

        private void scheduleProcessing(String userId) {
            workerPool.submit(() -> {
                BlockingQueue<Request> queue = userTaskQueues.get(userId);
                try {
                    while (!queue.isEmpty()) {
                        Request request = queue.poll();
                        if (request != null) {
                            requestHandler.accept(request);
                        }
                    }
                } finally {
                    // ~ reset flag
                    userProcessingFlags.put(userId, false);
                }
            });
        }
    }
}
