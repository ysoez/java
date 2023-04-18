package adapter;

import adapter.image.awesome_filters.Caramel;
import adapter.image.*;
import adapter.mail.EmailClient;
import adapter.mail.GmailAdapter;
import org.junit.jupiter.api.Test;

class AdapterPatternTest {

    @Test
    void test() {
        var imageView = new ImageView(new Image());
        imageView.apply(new VividFilter());
        imageView.apply(new CaramelFilter(new Caramel()));
        imageView.apply(new CaramelAdapter());
    }

    @Test
    void mailTest() {
        var client = new EmailClient();
        client.addProvider(new GmailAdapter());
        client.downloadEmails();
    }

}