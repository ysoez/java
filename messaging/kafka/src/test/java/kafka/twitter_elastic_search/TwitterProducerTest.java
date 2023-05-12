package kafka.twitter_elastic_search;

import kafka.KafkaTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;
import static kafka.twitter_elastic_search.TwitterClient.TWEETS_COUNT;
import static kafka.twitter_elastic_search.TwitterProducer.TWITTER_TWEETS_TOPIC;
import static org.awaitility.Awaitility.await;

class TwitterProducerTest extends KafkaTest {

    private final TwitterProducer producer = new TwitterProducer(KAFKA);

    @BeforeAll
    static void setup() {
        createTopic(TWITTER_TWEETS_TOPIC, 2);
    }

    @Test
    void shouldSendTweetsToKafka() {
        AtomicInteger atomicInteger = producer.sendTweets();
        await().atMost(30, SECONDS).until(() -> TWEETS_COUNT == atomicInteger.get());
    }

}