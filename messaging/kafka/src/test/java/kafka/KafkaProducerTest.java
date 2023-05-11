package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import static kafka.KafkaClientConfiguration.producerConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;

class KafkaProducerTest extends KafkaTest {

    @BeforeAll
    static void setupTopic() {
        createTopic(TEST_TOPIC, 3);
    }

    @Test
    void messagesWithTheSameKeyShouldBeInTheSamePartition() throws ExecutionException, InterruptedException {
        try (var producer = new KafkaProducer<>(producerConfig(KAFKA))) {
            var partitions = new HashSet<Integer>();
            for (int i = 0; i < 10; i++) {
                var record = producer.send(new ProducerRecord<>(TEST_TOPIC, "user-1", String.valueOf(i))).get();
                partitions.add(record.partition());
            }
            assertEquals(1, partitions.size());
        }
    }

}