package kafka.twitter;

import kafka.KafkaTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;
import static kafka.twitter.TwitterClient.TWEETS_COUNT;
import static kafka.twitter.TwitterProducer.TWITTER_TWEETS_TOPIC;
import static org.awaitility.Awaitility.await;

class TwitterProducerTest extends KafkaTest {

    private final TwitterProducer producer = new TwitterProducer(KAFKA);

    @BeforeAll
    static void setup() {
        createTopic(TWITTER_TWEETS_TOPIC, 2);
    }

    @Test
    void shouldSendTweetsToKafka() {
        AtomicInteger tweetsCount = producer.sendTweets();
        await().atMost(30, SECONDS).until(() -> TWEETS_COUNT == tweetsCount.get());
    }

}