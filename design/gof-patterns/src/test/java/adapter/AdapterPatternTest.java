package adapter;

import adapter.awesome_filters.Caramel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdapterPatternTest {

    @Test
    void test() {
        var imageView = new ImageView(new Image());
        imageView.apply(new VividFilter());
        imageView.apply(new CaramelFilter(new Caramel()));
        imageView.apply(new CaramelAdapter());
    }

}