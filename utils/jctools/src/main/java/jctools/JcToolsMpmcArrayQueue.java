package jctools;

import org.jctools.queues.MpmcArrayQueue;

import java.util.concurrent.atomic.AtomicInteger;

class JcToolsMpmcArrayQueue {

    private static final int PRODUCERS = 3;
    private static final int CONSUMERS = 2;
    private static final int TASKS_EACH = 4;
    private static final int TOTAL = PRODUCERS * TASKS_EACH;
    private static final AtomicInteger done = new AtomicInteger(0);

    @SuppressWarnings("BusyWait")
    public static void main(String[] args) throws InterruptedException {
        //
        // ~ bounded queue for thread pools and work-stealing schedulers where both sides scale
        // ~ slower than Mpsc/Spsc variants but the only correct choice when >1 consumer.
        //
        var queue = new MpmcArrayQueue<Runnable>(32);
        //
        // ~ multiple producers
        //
        for (int p = 0; p < PRODUCERS; p++) {
            final int pid = p;
            new Thread(() -> {
                for (int i = 0; i < TASKS_EACH; i++) {
                    final int taskId = pid * TASKS_EACH + i;
                    while (!queue.offer(() -> {
                        System.out.println("[" + Thread.currentThread().getName() + "] executed task-" + taskId);
                        done.incrementAndGet();
                    }))
                        Thread.onSpinWait();
                }
            }, "producer-" + p).start();
        }
        //
        // ~ multiple consumers
        //
        for (int c = 0; c < CONSUMERS; c++) {
            new Thread(() -> {
                while (done.get() < TOTAL) {
                    Runnable task = queue.poll();
                    if (task != null)
                        task.run();
                    else
                        Thread.onSpinWait();
                }
            }, "consumer-" + c).start();
        }
        while (done.get() < TOTAL)
            Thread.sleep(10);

    }
}