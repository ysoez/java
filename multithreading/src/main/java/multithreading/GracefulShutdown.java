package multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

class GracefulShutdown {

    public static void main(String[] args) throws InterruptedException {
        var stopped = new AtomicBoolean(false);
        var shutdownService = new ShutdownService(stopped);
        var service = new Service(stopped);
        var worker = new Thread(service);
        worker.start();
        TimeUnit.SECONDS.sleep(1);
        shutdownService.destroy();
    }

    private record Service(AtomicBoolean stopped) implements Runnable {
        @Override
        public void run() {
            while (!stopped.get()) {
                try {
                    System.out.println("do task");
                } catch (RuntimeException e) {
                    System.err.println("error occurred");
                }
            }
        }
    }

    private record ShutdownService(AtomicBoolean stopped) {
        public void destroy() {
            stopped.compareAndSet(false, true);
        }
    }

}
