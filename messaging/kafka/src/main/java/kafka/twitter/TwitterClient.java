package kafka.twitter;

import java.util.stream.Stream;

public class TwitterClient {

    public static final int TWEETS_COUNT = 10_000;

    Stream<String> tweetStream() {
        return Stream.generate(() -> "Message").limit(TWEETS_COUNT);
    }

}
