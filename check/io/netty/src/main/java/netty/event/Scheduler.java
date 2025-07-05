package netty.event;

import io.netty.channel.Channel;
import io.netty.channel.EventLoop;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class Scheduler {

    public static void schedule() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        ScheduledFuture<?> future = executor.schedule(
                () -> System.out.println("Now it is 60 seconds later"),
                60, TimeUnit.SECONDS
        );
        executor.shutdown();
    }

    public static void schedule(Channel ch) {
        try (EventLoop eventExecutor = ch.eventLoop()) {
            ScheduledFuture<?> future = eventExecutor.schedule(
                    () -> System.out.println("60 seconds later"),
                    60, TimeUnit.SECONDS
            );
        }
    }

    public static void scheduleFixed(Channel ch) {
        try (EventLoop eventExecutor = ch.eventLoop()) {
            ScheduledFuture<?> future = eventExecutor.scheduleAtFixedRate(
                    () -> System.out.println("Run every 60 seconds"),
                    60, 60, TimeUnit.SECONDS
            );
        }
    }

    public static void cancelTask(Channel ch) {
        try (EventLoop eventExecutor = ch.eventLoop()) {
            ScheduledFuture<?> future = eventExecutor.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Run every 60 seconds");
                        }
                    }, 60, 60, TimeUnit.SECONDS);
            // ~ some other code that runs...
            boolean mayInterruptIfRunning = false;
            future.cancel(mayInterruptIfRunning);
        }
    }

}
