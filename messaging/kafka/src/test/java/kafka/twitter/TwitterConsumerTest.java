package kafka.twitter;

import kafka.KafkaTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;
import static kafka.twitter.TwitterClient.TWEETS_COUNT;
import static kafka.twitter.TwitterProducer.TWITTER_TWEETS_TOPIC;
import static org.awaitility.Awaitility.await;

class TwitterConsumerTest extends KafkaTest {

    private final TwitterProducer producer = new TwitterProducer(KAFKA);
    private final TwitterConsumer consumer = new TwitterConsumer(KAFKA);

    @BeforeAll
    static void setup() {
        createTopic(TWITTER_TWEETS_TOPIC, 2);
    }

    @Test
    void shouldReadTweetsFromKafka() {
        producer.sendTweets();
        AtomicInteger tweetsCount = consumer.readTweets();
        await().atMost(30, SECONDS).until(() -> TWEETS_COUNT == tweetsCount.get());
    }

}