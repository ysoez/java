package jctools;

import org.jctools.queues.MpscUnboundedXaddArrayQueue;

class JcToolsMpscUnboundedXaddArrayQueue {

    public static void main(String[] args) throws InterruptedException {
        //
        // ~ unbounded queue: when burst size is unknown and dropping messages is unacceptable
        // ~ watch memory: unbounded means unlimited heap growth under overload.
        // ~ grows dynamically: uses XADD (fetch-and-add) for cheap multi-producer offers
        // ~ chunkSize: controls internal chunk allocation granularity
        //
        var queue = new MpscUnboundedXaddArrayQueue<String>(32);
        //
        // ~ producer
        //
        var producer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                //
                // ~ never blocks and never returns false
                //
                queue.offer("event-" + i);
            }
            System.out.println("producer offered 100 events");
        });
        producer.start();
        //
        // ~ wait for producer
        //
        producer.join();
        System.out.println("queue size before drain: " + queue.size());
        //
        // ~ drain() is the idiomatic consumer API that processes a batch per call
        //
        int[] totalDrained = {0};
        while (!queue.isEmpty()) {
            int n = queue.drain(item -> {
                //
                // ~ called for each element in the batch
                //
                if (totalDrained[0] < 5 || totalDrained[0] >= 95) {
                    System.out.println("consumed: " + item);
                } else if (totalDrained[0] == 5) {
                    System.out.println("consumed: " + item + " (skipping middle)");
                }
                totalDrained[0]++;
            });
            if (n == 0)
                break;
        }
        System.out.println("\ntotal drained: " + totalDrained[0]);
    }
}