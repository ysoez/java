package flyweight;

import org.junit.jupiter.api.Test;

class FlyweightPatternTest {

    @Test
    void test() {
        var service = new PointService(new PointIconFactory());
        for (Point point : service.getPoints()) {
            point.draw();
        }
    }

}