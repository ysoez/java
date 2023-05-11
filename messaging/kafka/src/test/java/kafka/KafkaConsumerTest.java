package kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static kafka.KafkaClientConfiguration.consumerConfig;
import static kafka.KafkaClientConfiguration.producerConfig;
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

}