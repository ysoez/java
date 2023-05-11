package kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.testcontainers.containers.KafkaContainer;

import java.util.Properties;


//
//        // high throughput producer ()
//        props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
//        props.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
//        props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, String.valueOf(32 * 1024)); // 32 kb
//
//        return props;
//    }
public class KafkaClientConfiguration {

    public enum Producer {
        SAFE {
            @Override
            Properties properties() {
                var props = new Properties();
                props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
                props.setProperty(ProducerConfig.ACKS_CONFIG, "all");
                props.setProperty(ProducerConfig.RETRIES_CONFIG, String.valueOf(Integer.MAX_VALUE));
                props.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");
                return props;
            }
        },
        //
        HIGH_THROUGHPUT {
            @Override
            Properties properties() {
                var props = new Properties();
                props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
                props.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
                props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, String.valueOf(32 * 1024)); // 32 kb
                return props;
            }
        };

        abstract Properties properties();
    }

    public static Properties producerConfig(KafkaContainer kafka, Producer... configs) {
        var props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        for (Producer producer : configs) {
            props.putAll(producer.properties());
        }
        return props;
    }

}
