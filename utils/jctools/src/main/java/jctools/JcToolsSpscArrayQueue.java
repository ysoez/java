package jctools;

import org.jctools.queues.SpscArrayQueue;

class JcToolsSpscArrayQueue {

    public static void main(String[] args) throws InterruptedException {
        //
        // ~  wait-free bounded queue
        //
        var queue = new SpscArrayQueue<Integer>(16);
        //
        // ~ single-producer
        //
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                //
                // ~ spin until space available
                //
                while (!queue.offer(i)) {
                    Thread.onSpinWait();
                }
                System.out.println("produced: " + i);
            }
        }, "producer");
        //
        // ~ single-consumer
        //
        Thread consumer = new Thread(() -> {
            int received = 0;
            while (received < 10) {
                Integer item = queue.poll();
                if (item != null) {
                    System.out.println("consumed: " + item);
                    received++;
                } else {
                    Thread.onSpinWait();
                }
            }
        }, "consumer");

        consumer.start();
        producer.start();

        producer.join();
        consumer.join();

        System.out.println("queue empty: " + queue.isEmpty());
    }

}
