package jctools;

import org.jctools.queues.SpscLinkedQueue;

class JcToolsSpscLinkedQueue {

    public static void main(String[] args) throws InterruptedException {
        //
        // ~ unbounded queue for single-producer pipelines where back-pressure isn't needed
        //
        var queue = new SpscLinkedQueue<String>();
        //
        // ~ produce 5 items
        //
        Thread producer = new Thread(() -> {
            String[] items = {"alpha", "beta", "gamma", "delta", "epsilon"};
            for (String item : items) {
                queue.offer(item); // always succeeds — unbounded
                System.out.println("[producer] offered: " + item);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        //
        // ~ consume 5 items
        //
        Thread consumer = new Thread(() -> {
            int received = 0;
            while (received < 5) {
                String head = queue.peek();
                if (head != null) {
                    System.out.println("[consumer] peek:  " + head);
                    String item = queue.poll();
                    System.out.println("[consumer] poll:  " + item);
                    received++;
                } else {
                    Thread.onSpinWait();
                }
            }
        });

        consumer.start();
        producer.start();

        producer.join();
        consumer.join();
    }
}