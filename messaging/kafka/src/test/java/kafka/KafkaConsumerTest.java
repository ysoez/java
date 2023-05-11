package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static kafka.KafkaClientConfiguration.consumerConfig;
import static kafka.KafkaClientConfiguration.producerConfig;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

class KafkaConsumerTest extends KafkaTest {

    @BeforeAll
    static void setupTopic() {
        createTopic(TEST_TOPIC, 3);
    }

    @Test
    void readMessage() throws ExecutionException, InterruptedException {
        try (var consumer = new KafkaConsumer<String, String>(consumerConfig(KAFKA));
             var producer = new KafkaProducer<String, String>(producerConfig(KAFKA))) {
            consumer.subscribe(List.of(TEST_TOPIC));
            producer.send(new ProducerRecord<>(TEST_TOPIC, "user-1", "hello")).get();
            for (var record : consumer.poll(Duration.ofSeconds(1))) {
                assertEquals("hello", record.value());
            }
        }
    }

    @Test
    void runnableConsumer() throws InterruptedException, ExecutionException {
        try (var producer = new KafkaProducer<String, String>(producerConfig(KAFKA))) {
            producer.send(new ProducerRecord<>(TEST_TOPIC, "user-1", "Hello")).get();
        }
        var latch = new CountDownLatch(1);
        var consumerRunnable = new ConsumerRunnable(latch, consumerConfig(KAFKA), Set.of(TEST_TOPIC));
        Thread thread = new Thread(consumerRunnable);
        thread.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            consumerRunnable.shutdown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
        latch.await();
    }

    @Test
    @Disabled("fix me")
    void rebalanceConsumerGroups() {
        var consumer1 = new KafkaConsumer<String, String>(consumerConfig(KAFKA));
        var consumer2 = new KafkaConsumer<String, String>(consumerConfig(KAFKA));
        var consumer3 = new KafkaConsumer<String, String>(consumerConfig(KAFKA));

        consumer1.subscribe(List.of(TEST_TOPIC));
        consumer2.subscribe(List.of(TEST_TOPIC));
        consumer3.subscribe(List.of(TEST_TOPIC));

        await().atMost(5, TimeUnit.SECONDS).until(() -> consumer2.assignment().size() == 1);
        await().atMost(5, TimeUnit.SECONDS).until(() -> consumer3.assignment().size() == 1);
        await().atMost(5, TimeUnit.SECONDS).until(() -> consumer1.assignment().size() == 1);

        consumer3.close();
        await().atMost(5, TimeUnit.SECONDS).until(() -> consumer1.assignment().size() + consumer2.assignment().size() == 3);

        consumer2.close();
        await().atMost(5, TimeUnit.SECONDS).until(() -> consumer1.assignment().size() == 3);

        consumer1.close();
    }

    @Test
    void assignAndSeekApi() throws ExecutionException, InterruptedException {
        try (var consumer = new KafkaConsumer<String, String>(consumerConfig(KAFKA));
             var producer = new KafkaProducer<String, String>(producerConfig(KAFKA))) {
            int partition = 0;
            for (int i = 0; i < 10; i++) {
                partition = producer.send(new ProducerRecord<>(TEST_TOPIC, "user-1", String.valueOf(i))).get().partition();
            }
            var topicPartition = new TopicPartition(TEST_TOPIC, partition);
            consumer.assign(List.of(topicPartition));
            long offset = 5;
            consumer.seek(topicPartition, offset);

            int messagesToRead = 5;
            int messageRecevied = 0;
            boolean keepReading = true;
            List<Integer> messages = new ArrayList<>();
            while (keepReading) {
                for (ConsumerRecord<String, String> record : consumer.poll(Duration.ofMillis(100))) {
                    messageRecevied++;
                    messages.add(Integer.valueOf(record.value()));
                    if (messageRecevied >= messagesToRead) {
                        keepReading = false;
                        break;
                    }
                }
            }
            messages.sort(Comparator.naturalOrder());
            assertEquals(List.of(5, 6, 7, 8, 9), messages);
        }
    }

}