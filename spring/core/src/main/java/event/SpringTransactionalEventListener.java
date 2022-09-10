package event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpringTransactionalEventListener {

    private final EntityStateChecker entityStateChecker;

    @TransactionalEventListener
    public void handleEvent(TaskEvent taskEvent) {
        Task task = taskEvent.getTask();
        log.info("Is task transient? {}", entityStateChecker.isTransient(task));
        log.info("Is task managed? {}", entityStateChecker.isManaged(task));
        log.info("Is task detached? {}", entityStateChecker.isDetached(task));
    }

}
