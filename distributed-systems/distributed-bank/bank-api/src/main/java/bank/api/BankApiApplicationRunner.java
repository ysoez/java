package bank.api;

import bank.api.mock.IncomingTransactionLoader;
import bank.api.mock.UserResidenceDatabase;
import bank.api.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
public class BankApiApplicationRunner {

    private static final String SUSPICIOUS_TRANSACTIONS_TOPIC = "suspicious-transactions";
    private static final String VALID_TRANSACTIONS_TOPIC = "valid-transactions";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    public static void main(String[] args) {
        Producer<String, Transaction> kafkaProducer = createKafkaProducer();
        var incomingTransactionsReader = new IncomingTransactionLoader();
        var userResidenceDatabase = new UserResidenceDatabase();
        try {
            processTransactions(incomingTransactionsReader, userResidenceDatabase, kafkaProducer);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Transaction processing error", e);
        } finally {
            kafkaProducer.flush();
            kafkaProducer.close();
        }
    }

    private static void processTransactions(IncomingTransactionLoader transactionLoader,
                                           UserResidenceDatabase userResidenceDatabase,
                                           Producer<String, Transaction> kafkaProducer) throws ExecutionException, InterruptedException {
        while (transactionLoader.hasNext()) {
            Transaction transaction = transactionLoader.next();
            String userResidence = userResidenceDatabase.getUserResidence(transaction.user());
            ProducerRecord<String, Transaction> record;

            if (userResidence.equals(transaction.transactionLocation())) {
                record = new ProducerRecord<>(VALID_TRANSACTIONS_TOPIC, transaction.user(), transaction);
            } else {
                record = new ProducerRecord<>(SUSPICIOUS_TRANSACTIONS_TOPIC, transaction.user(), transaction);
            }

            RecordMetadata recordMetadata = kafkaProducer.send(record).get();
            log.debug("Record with (key: {}, value: {}), was sent to (partition: {}, offset: {}, topic: {})", record.key(),
                    record.value(),
                    recordMetadata.partition(),
                    recordMetadata.offset(),
                    recordMetadata.topic());
        }
    }

    private static Producer<String, Transaction> createKafkaProducer() {
        var properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "banking-api-producer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Transaction.KafkaJsonSerializer.class.getName());
        return new KafkaProducer<>(properties);
    }

}
