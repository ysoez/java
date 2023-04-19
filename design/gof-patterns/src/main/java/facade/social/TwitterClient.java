package facade.social;

import java.util.ArrayList;
import java.util.List;

class TwitterClient {

    public List<Tweet> getRecentTweets(String accessToken) {
        System.out.println("Getting recent tweets");
        return new ArrayList<>();
    }

}
