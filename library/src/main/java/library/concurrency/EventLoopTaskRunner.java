package library.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static library.concurrency.ConcurrencyUtils.*;

class EventLoopTaskRunner {

    public static void main(String[] args) throws InterruptedException {
        try (var eventLoop = new SingleThreadEventLoop()) {
            eventLoop.start();
            eventLoop.submit(() -> System.out.println("task 1"));
            eventLoop.submit(() -> System.out.println("task 2"));
            eventLoop.submit(() -> System.out.println("task 3"));
            sleep(1, TimeUnit.SECONDS);
        }
        logMain("event loop stopped");
    }

    private static class SingleThreadEventLoop implements AutoCloseable {

        private final BlockingQueue<Runnable> taskQueue;
        private volatile boolean running;

        public SingleThreadEventLoop() {
            taskQueue = new LinkedBlockingQueue<>();
            running = true;
        }

        public void start() {
            Thread eventLoopThread = new Thread(() -> {
                while (running || !taskQueue.isEmpty()) {
                    // ~ use non-blocking poll() instead of blocking take() for graceful shutdown
                    Runnable task = taskQueue.poll();
                    if (task != null) {
                        task.run();
                    }
                }
                logWorker("terminated");
            }, "event-loop");
            eventLoopThread.start();
        }

        public void submit(Runnable task) {
            taskQueue.offer(task);
        }

        @Override
        public void close() {
            running = false;
        }
    }
}
