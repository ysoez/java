package bank.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

import java.math.BigDecimal;

@Slf4j
public record Transaction(String user, BigDecimal amount, String transactionLocation) {

    public static class KafkaJsonSerializer implements Serializer<Transaction> {
        private static final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public byte[] serialize(String topic, Transaction data) {
            byte[] serializedData = null;
            try {
                serializedData = objectMapper.writeValueAsString(data).getBytes();
            } catch (Exception e) {
                log.error("Kafka serialization failed", e);
            }
            return serializedData;
        }
    }

}
