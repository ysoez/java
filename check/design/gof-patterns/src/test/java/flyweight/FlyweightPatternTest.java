package flyweight;

import flyweight.excel.CellContextFactory;
import flyweight.excel.SpreadSheet;
import org.junit.jupiter.api.Test;

class FlyweightPatternTest {

    @Test
    void test() {
        var service = new PointService(new PointIconFactory());
        for (Point point : service.getPoints()) {
            point.draw();
        }
    }

    @Test
    void excelTest() {
        var contextFactory = new CellContextFactory();
        var sheet = new SpreadSheet(contextFactory);
        sheet.setContent(0, 0, "Hello");
        sheet.setContent(1, 0, "World");
        sheet.setFontFamily(0, 0, "Arial");
        sheet.render();
    }

}