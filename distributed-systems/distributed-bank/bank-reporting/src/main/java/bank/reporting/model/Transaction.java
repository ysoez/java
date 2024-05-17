package bank.reporting.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.math.BigDecimal;

@Slf4j
public record Transaction(String user, BigDecimal amount, String transactionLocation) {

    public static class KafkaJsonDeserializer implements Deserializer<Transaction> {
        @Override
        public Transaction deserialize(String topic, byte[] data) {
            ObjectMapper mapper = new ObjectMapper();
            Transaction transaction = null;
            try {
                transaction = mapper.readValue(data, Transaction.class);
            } catch (Exception e) {
                log.error("Kafka deserialization failed", e);
            }
            return transaction;

        }
    }
}
