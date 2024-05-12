package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Properties;

public class KafkaUtils {

    public static Producer<Long, String> newKafkaProducer(String bootstrapServers, String clientId,
                                                          Class<? extends Serializer<?>> key,
                                                          Class<? extends Serializer<?>> value) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, key.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, value.getName());
        return new KafkaProducer<>(properties);
    }

}
