package facade;

import org.junit.jupiter.api.Test;

import javax.print.DocFlavor;

import static org.junit.jupiter.api.Assertions.*;

class FacadePatternTest {

    @Test
    void test() {
        var service = new NotificationService();
        service.send("hello", "targetDevice");
    }

}