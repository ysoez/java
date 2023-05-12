package kafka.twitter;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.testcontainers.containers.KafkaContainer;

import java.util.concurrent.atomic.AtomicInteger;

import static kafka.KafkaClientConfiguration.Producer.HIGH_THROUGHPUT;
import static kafka.KafkaClientConfiguration.Producer.SAFE;
import static kafka.KafkaClientConfiguration.producerConfig;

@Slf4j
class TwitterProducer {
    static final String TWITTER_TWEETS_TOPIC = "twitter_tweets";
    private final TwitterClient twitterClient;
    private final KafkaProducer<String, String> kafkaProducer;

    TwitterProducer(KafkaContainer kafka) {
        twitterClient = new TwitterClient();
        kafkaProducer = new KafkaProducer<>(producerConfig(kafka, SAFE, HIGH_THROUGHPUT));
    }

    AtomicInteger sendTweets() {
        AtomicInteger tweetsCount = new AtomicInteger(0);
        try {
            twitterClient.tweetStream().forEach(tweet -> kafkaProducer.send(new ProducerRecord<>(TWITTER_TWEETS_TOPIC, tweet),
                    (metadata, exception) -> {
                        if (exception != null) {
                            log.error("Failed to send a message to a broker", exception);
                        } else {
                            log.debug("Sent a tweet Kafka: {}", metadata);
                            tweetsCount.getAndIncrement();
                        }
                    }
            ));
            return tweetsCount;
        } finally {
            kafkaProducer.close();
        }
    }

}
