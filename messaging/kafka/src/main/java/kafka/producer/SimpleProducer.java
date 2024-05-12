package kafka.producer;

import kafka.KafkaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

@Slf4j
public class SimpleProducer {

    private static final String TOPIC = "events";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    public static void main(String[] args) {
        Producer<Long, String> kafkaProducer = KafkaUtils.newKafkaProducer(
                BOOTSTRAP_SERVERS,
                "events-producer",
                LongSerializer.class,
                StringSerializer.class
        );
        try {
            produceMessages(10, kafkaProducer);
        } catch (Exception e) {
            log.error("Failed to produce messages", e);
        } finally {
            kafkaProducer.flush();
            kafkaProducer.close();
        }
    }

    public static void produceMessages(int numberOfMessages, Producer<Long, String> kafkaProducer) throws Exception {
        int partition = 1;
        for (int i = 0; i < numberOfMessages; i++) {
            long key = i;
            String value = String.format("event %d", i);
            long timeStamp = System.currentTimeMillis();
            ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC, partition, timeStamp, key, value);
            RecordMetadata recordMetadata = kafkaProducer.send(record).get();
            log.debug("Record(key: {}, value: {}), was sent to topic(name: {}, partition: {}, offset: {})",
                    record.key(), record.value(), record.topic(), recordMetadata.partition(), recordMetadata.offset());
        }
    }

}
