package kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Properties;

public class KafkaUtils {

    public static Producer<Long, String> newKafkaProducer(String bootstrapServers, String clientId,
                                                          Class<? extends Serializer<?>> key,
                                                          Class<? extends Serializer<?>> value) {
        var properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, key.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, value.getName());
        return new KafkaProducer<>(properties);
    }

    public static Consumer<Long, String> newKafkaConsumer(String bootstrapServers, String consumerGroup,
                                                          Class<? extends Serializer<?>> key,
                                                          Class<? extends Serializer<?>> value) {
        var properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, key.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, value.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return new KafkaConsumer<>(properties);
    }

}
