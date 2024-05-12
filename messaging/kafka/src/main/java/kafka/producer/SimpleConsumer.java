package kafka.producer;

import kafka.KafkaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;

@Slf4j
public class SimpleConsumer {

    private static final String TOPIC = "events";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    public static void main(String[] args) {
        String consumerGroup = "defaultConsumerGroup";
        if (args.length == 1) {
            consumerGroup = args[0];
        }
        log.debug("Consumer is part of consumer group: {}", consumerGroup);
        Consumer<Long, String> kafkaConsumer = KafkaUtils.newKafkaConsumer(
                BOOTSTRAP_SERVERS,
                consumerGroup,
                LongSerializer.class,
                StringSerializer.class
        );
        consumeMessages(TOPIC, kafkaConsumer);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public static void consumeMessages(String topic, Consumer<Long, String> kafkaConsumer) {
        kafkaConsumer.subscribe(Collections.singletonList(topic));
        while (true) {
            // ~ read records or block at most 1 second
            ConsumerRecords<Long, String> records = kafkaConsumer.poll(Duration.ofSeconds(1));
            if (records.isEmpty()) {
                log.debug("No records, do something else");
            }
            for (ConsumerRecord<Long, String> record : records) {
                log.debug("Received record(key: {}, value: {}) from topic(name: {}, partition: {}, offset: {})",
                        record.key(), record.value(), record.topic(), record.partition(), record.offset());
            }
            kafkaConsumer.commitAsync();
        }
    }

}
