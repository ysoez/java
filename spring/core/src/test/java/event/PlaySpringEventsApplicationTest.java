package event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlaySpringEventsApplicationTest {

    @Autowired
    private TaskService taskService;

    @Test
    void syncEventListener() {
        taskService.create("test spring events");
    }

}
