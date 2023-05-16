package concurrency.producer_consumer;

import java.util.Random;

class MessageQueue {

    // message sent from producer to consumer.
    private String message;

    // true if consumer should wait for producer to send message,
    // false if producer should wait for consumer to retrieve message.
    private boolean empty = true;

    public synchronized String take() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        empty = true;
        notifyAll();
        return message;
    }

    public synchronized void put(String message) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                /* no op */
            }
        }
        empty = false;
        this.message = message;
        notifyAll();
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            var random = new Random();
            MessageQueue queue = MessageQueue.this;
            for (String message = queue.take(); !message.equals("Done"); message = queue.take()) {
                System.out.format("Received message: %s%n", message);
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {
                    /* no op */
                }
            }
        }
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            String[] messages = {
                    "Java",
                    "JavaScript",
                    "TypeScript"
            };
            var random = new Random();
            var queue = MessageQueue.this;
            for (int i = 0; i < messages.length; i++) {
                queue.put(messages[i]);
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {
                    /* no op */
                }
            }
            queue.put("Done");
        }
    }

}
