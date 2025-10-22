package multithreading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RequestProcessing {

    private static final RequestHandler REQUEST_HANDLER = new RequestHandler();

    private static final RequestProcessor LOCK_PER_USER = new LockPerUser();
    private static final RequestProcessor THREAD_PER_USER = new ThreadPerUser();
    private static final RequestProcessor QUEUE_PER_USER = new BatchedQueuePerUser();

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
        try (RequestProcessor processor = QUEUE_PER_USER) {
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
            //
            // ~ shares threads efficiently across users
            // ~ incurs lock acquire/release overhead per request
            //
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
            //
            // ~ avoids lock contention
            // ~ inefficient with many users (dedicated threads means resource overhead and context switching)
            //
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

    private static class BatchedQueuePerUser implements RequestProcessor {

        private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        private final ConcurrentHashMap<String, UserQueue> userQueues = new ConcurrentHashMap<>();

        @Override
        public void process(List<Request> requests) {
            //
            // ~ shines when users have bursty or multiple requests (amortizes costs over batches)
            //
            requests.forEach(request -> {
                UserQueue userQueue = userQueues.computeIfAbsent(request.userId, _ -> new UserQueue());
                userQueue.queue.offer(() -> REQUEST_HANDLER.accept(request));
                if (userQueue.processing.compareAndSet(false, true)) {
                    executor.submit(() -> drainQueue(userQueue));
                }
            });
        }

        @Override
        public void close() {
            executor.close();
        }

        private void drainQueue(UserQueue userQueue) {
            try {
                //
                // ~ execute sequentially pending requests
                //
                Runnable task;
                while ((task = userQueue.queue.poll()) != null) {
                    task.run();
                }
            } finally {
                userQueue.processing.set(false);
                //
                // ~ post drain if items were added during processing
                //
                if (!userQueue.queue.isEmpty() && userQueue.processing.compareAndSet(false, true)) {
                    executor.submit(() -> drainQueue(userQueue));
                }
            }
        }

        private static class UserQueue {
            final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
            final AtomicBoolean processing = new AtomicBoolean(false);
        }
    }

}
