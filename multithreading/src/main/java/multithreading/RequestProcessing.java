package multithreading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RequestProcessing {

    private static final RequestHandler REQUEST_HANDLER = new RequestHandler();

    private static final RequestProcessor LOCK_PER_USER = new LockPerUser();
    private static final RequestProcessor THREAD_PER_USER = new ThreadPerUser();

    public static void main(String[] args) throws Exception {
        //
        // ~ 5 users & 10 requests
        //
        List<Request> requests = new ArrayList<>();
        Map<String, Integer> taskCountPerUser = new HashMap<>();
        for (int i = 1; i <= 50; i++) {
            String userId = "user#" + (i % 5);
            var taskCount = taskCountPerUser.getOrDefault(userId, 0);
            taskCountPerUser.put(userId, ++taskCount);
            requests.add(new Request(taskCount, userId));
        }
        //
        // ~ handle requests concurrently among users but sequentially for the same user
        //
        try (RequestProcessor processor = LOCK_PER_USER) {
            processor.process(requests);
        }
    }

    private record Request(Integer requestId, String userId) {}

    private static class RequestHandler implements Consumer<Request> {
        @Override
        public void accept(Request request) {
            var latency = ThreadLocalRandom.current().nextLong(100, 1000);
            LockSupport.parkNanos(MILLISECONDS.toNanos(latency));
            System.out.printf("%s took: %sms thread: %s\n", request, latency, Thread.currentThread().getName());
        }
    }

    private interface RequestProcessor extends AutoCloseable {
        void process(List<Request> requests);
    }

    private static class LockPerUser implements RequestProcessor {

        private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        private final ConcurrentMap<String, ReentrantLock> userLocks = new ConcurrentHashMap<>();

        @Override
        public void process(List<Request> requests) {
            requests.forEach(request -> executor.submit(() -> {
                ReentrantLock lock = userLocks.computeIfAbsent(request.userId, _ -> new ReentrantLock());
                lock.lock();
                try {
                    REQUEST_HANDLER.accept(request);
                } finally {
                    lock.unlock();
                }
            }));
        }

        @Override
        public void close() throws Exception {
            executor.close();
        }
    }

    private static class ThreadPerUser implements RequestProcessor {

        private final ConcurrentHashMap<String, ExecutorService> userExecutors = new ConcurrentHashMap<>();

        @Override
        public void process(List<Request> requests) {
            requests.forEach(request -> {
                ExecutorService userExecutor = userExecutors.computeIfAbsent(request.userId, _ -> Executors.newSingleThreadExecutor());
                userExecutor.submit(() -> REQUEST_HANDLER.accept(request));
            });
        }

        @Override
        public void close() {
            userExecutors.values().forEach(ExecutorService::close);
        }
    }

    private static class QueuePerUser implements RequestProcessor {

        private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        private final ConcurrentHashMap<String, UserQueue> userQueues = new ConcurrentHashMap<>();

        @Override
        public void process(List<Request> requests) {
            requests.forEach(request -> {
                UserQueue userQueue = userQueues.computeIfAbsent(request.userId, _ -> new UserQueue());
                userQueue.queue.offer(() -> REQUEST_HANDLER.accept(request));
                executor.submit(() -> processQueue(request.userId));
            });
        }

        @Override
        public void close() throws Exception {

        }

        private void processQueue(String userId) {
            UserQueue userQueue = userQueues.get(userId);
            if (userQueue == null)
                return;
            if (userQueue.semaphore.tryAcquire()) {
                try {
                    while (!userQueue.queue.isEmpty()) {
                        Runnable task = userQueue.queue.poll();
                        if (task != null) {
                            task.run(); // Execute sequentially
                        }
                    }
                } finally {
                    userQueue.semaphore.release();
                }
            }
        }

        private static class UserQueue {
            final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
            final Semaphore semaphore = new Semaphore(1);
        }
    }

}
