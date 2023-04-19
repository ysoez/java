package facade;

import facade.social.TwitterAPI;
import org.junit.jupiter.api.Test;

import javax.print.DocFlavor;

import static org.junit.jupiter.api.Assertions.*;

class FacadePatternTest {

    @Test
    void test() {
        var service = new NotificationService();
        service.send("hello", "targetDevice");
    }

    @Test
    void twitterTest() {
        var twitterAPI = new TwitterAPI("appKey", "secret");
        var tweets = twitterAPI.getRecentTweets();
    }


}