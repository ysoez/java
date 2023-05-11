package kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Slf4j
class ConsumerRunnable implements Runnable {

    private final CountDownLatch latch;
    private final KafkaConsumer<String, String> consumer;

    public ConsumerRunnable(CountDownLatch latch, Properties properties, Collection<String> topics) {
        this.latch = latch;
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(topics);
    }

    @Override
    public void run() {
        while (true) {
            try {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records)
                    log.debug("Received message: {}", record);
            } catch (WakeupException e) {
                log.warn("Received interrupt signal for poll", e);
            } finally {
                consumer.close();
                latch.countDown();
            }
        }
    }

    public void shutdown() {
        consumer.wakeup();
    }

}
