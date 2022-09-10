package event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpringSynchronousEventListener {

    private final EntityStateChecker entityStateChecker;

    @EventListener
    public void handleEvent(TaskEvent taskEvent) {
        Task task = taskEvent.getTask();
        log.info("Is task transient? {}", entityStateChecker.isTransient(task));
        log.info("Is task managed? {}", entityStateChecker.isManaged(task));
        log.info("Is task detached? {}", entityStateChecker.isDetached(task));
    }

}
