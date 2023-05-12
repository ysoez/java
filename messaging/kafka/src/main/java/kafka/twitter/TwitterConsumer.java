package kafka.twitter;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaException;
import org.testcontainers.containers.KafkaContainer;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static kafka.KafkaClientConfiguration.consumerConfig;
import static kafka.twitter.TwitterClient.TWEETS_COUNT;
import static kafka.twitter.TwitterProducer.TWITTER_TWEETS_TOPIC;

@Slf4j
class TwitterConsumer {

    private final KafkaConsumer<String, String> kafkaConsumer;

    TwitterConsumer(KafkaContainer kafka) {
        kafkaConsumer = new KafkaConsumer<>(consumerConfig(kafka));
    }

    AtomicInteger readTweets() {
        var tweetsCount = new AtomicInteger(0);
        try {
            kafkaConsumer.subscribe(List.of(TWITTER_TWEETS_TOPIC));
            while (tweetsCount.get() != TWEETS_COUNT) {
                for (var record : kafkaConsumer.poll(Duration.ofMillis(100))) {
                    log.debug("Received message from Kafka: {}", record);
                    tweetsCount.incrementAndGet();
                }
            }
            return tweetsCount;
        } catch (KafkaException e) {
            log.error("Failed to process all messages: {}/{}", tweetsCount, TWEETS_COUNT);
            throw e;
        } finally {
            kafkaConsumer.close();
        }
    }

}
