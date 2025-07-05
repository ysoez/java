package bank.notification;

import bank.notification.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
public class BankNotificationApplicationRunner {

    private static final String SUSPICIOUS_TRANSACTIONS_TOPIC = "suspicious-transactions";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    public static void main(String[] args) {
        String consumerGroup = "user-notification-service";
        log.debug("Consumer is part of consumer group {}", consumerGroup);
        Consumer<String, Transaction> kafkaConsumer = createKafkaConsumer(consumerGroup);
        consumeMessages(SUSPICIOUS_TRANSACTIONS_TOPIC, kafkaConsumer);
    }

    public static void consumeMessages(String topic, Consumer<String, Transaction> kafkaConsumer) {
        kafkaConsumer.subscribe(Collections.singletonList(topic));
        while (true) {
            ConsumerRecords<String, Transaction> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
            if (consumerRecords.isEmpty()) {
                continue;
            }
            for (ConsumerRecord<String, Transaction> record : consumerRecords) {
                log.debug("Received record (key: {}, value: {}, partition: {}, offset: {}\n\n",
                        record.key(), record.value(), record.partition(), record.offset());
                sendUserNotification(record.value());
            }
        }
    }

    private static Consumer<String, Transaction> createKafkaConsumer(String consumerGroup) {
        var properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Transaction.KafkaJsonDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        return new KafkaConsumer<>(properties);
    }

    private static void sendUserNotification(Transaction transaction) {
        log.debug("Sending user {} notification about a suspicious transaction of {} in their account originating in {}",
                transaction.user(),
                transaction.amount(),
                transaction.transactionLocation());
    }

}
