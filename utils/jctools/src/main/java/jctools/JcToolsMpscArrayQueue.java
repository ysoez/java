package jctools;

import org.jctools.queues.MpscArrayQueue;

import java.util.concurrent.CountDownLatch;

class JcToolsMpscArrayQueue {

    private static final int PRODUCERS = 4;
    private static final int MESSAGES_PER_PRODUCER = 5;
    private static final int MESSAGES_TOTAL = PRODUCERS * MESSAGES_PER_PRODUCER;

    public static void main(String[] args) {
        //
        // ~ bounded queue for fan-in patterns (many worker threads hand off to one event loop or dispatcher)
        // ~ classic in Netty, Disruptor-style frameworks, and actor loops
        //
        var queue = new MpscArrayQueue<String>(64);
        var ready = new CountDownLatch(PRODUCERS);
        //
        // ~ 4 producer threads
        //
        for (int p = 0; p < PRODUCERS; p++) {
            final int id = p;
            Thread t = new Thread(() -> {
                ready.countDown();
                try {
                    ready.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                //
                // ~ produce messages
                //
                for (int i = 0; i < MESSAGES_PER_PRODUCER; i++) {
                    String msg = "P" + id + "-msg" + i;
                    while (!queue.offer(msg)) Thread.onSpinWait();
                    System.out.println("[producer-" + id + "] offered: " + msg);
                }
            }, "producer-" + p);
            t.setDaemon(true);
            t.start();
        }
        //
        // ~ single consumer drains all messages
        //
        int consumed = 0;
        while (consumed < MESSAGES_TOTAL) {
            String msg = queue.poll();
            if (msg != null) {
                System.out.println("consumed: " + msg);
                consumed++;
            } else {
                Thread.onSpinWait();
            }
        }
    }
}