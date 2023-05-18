package concurrency.producer_consumer;

import org.junit.jupiter.api.Test;

class MessageQueueTest {

    @Test
    void producerConsumer() throws InterruptedException {
        var queue = new MessageQueue();

        var producer = new Thread(queue.new Producer());
        producer.start();

        var consumer = new Thread(queue.new Consumer());
        consumer.start();

        producer.join();
        consumer.join();
    }

}