package jctools;

import org.jctools.queues.MpscArrayQueue;

class JcToolsDrainBatch {

    public static void main(String[] args) {
        //
        // ~ bounded batch processing for event loops that must yield after a max work quantum to avoid starvation
        // ~ Netty uses this pattern internally
        //
        var queue = new MpscArrayQueue<Integer>(64);
        //
        // ~ fill queue with 50 items
        //
        for (int i = 0; i < 50; i++)
            queue.offer(i);
        //
        // ~ drain in batches of 10
        //
        int batchSize = 10;
        int round = 0;
        while (!queue.isEmpty()) {
            round++;
            System.out.println("--- batch " + round + " ---");
            int drained = queue.drain(item -> System.out.println("  processed: " + item), batchSize);
            System.out.println("drained: " + drained + ", remaining: " + queue.size());
        }
        System.out.println("\nall done in " + round + " batches.");
    }
}