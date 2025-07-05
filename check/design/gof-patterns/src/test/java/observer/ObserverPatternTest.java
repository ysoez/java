package observer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObserverPatternTest {

    @Test
    void updateTest() {
        var dataSource = new DataSource();
        var sheet1 = new SpreadSheet(dataSource);
        var sheet2 = new SpreadSheet(dataSource);
        var chart = new Chart(dataSource);

        dataSource.add(sheet1);
        dataSource.add(sheet2);
        dataSource.add(chart);

        dataSource.setValue(10);
        dataSource.setValue(20);
        dataSource.setValue(30);
    }

}